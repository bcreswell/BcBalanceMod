//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.actions.unique;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CreateRandomCardAction extends AbstractGameAction
{
    boolean upgraded;
    boolean includeColorless;
    boolean includeForeign;
    boolean includeNative;
    AbstractCard.CardType cardType;
    AbstractCard.CardRarity cardRarity;
    boolean allowHealing;
    boolean makeItFree;
    boolean isTemporary;
    String exceptCard;
    
    public CreateRandomCardAction(boolean upgraded, boolean includeColorless, boolean includeForeign, boolean includeNative, AbstractCard.CardType cardType, AbstractCard.CardRarity rarity, boolean allowHealing, boolean makeItFree, boolean isTemporary, String exceptCard)
    {
        this.upgraded = upgraded;
        this.includeColorless = includeColorless;
        this.includeForeign = includeForeign;
        this.includeNative = includeNative;
        this.cardType = cardType;
        this.cardRarity = rarity;
        this.allowHealing = allowHealing;
        this.makeItFree = makeItFree;
        this.isTemporary = isTemporary;
        this.exceptCard = exceptCard;
        
        actionType = ActionType.CARD_MANIPULATION;
        duration = .25f;
        startDuration = duration;
    }
    
    public void update()
    {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            isDone = true;
        }
        else if (duration == startDuration)
        {
            AbstractCard card = null;
            while(card == null)
            {
                card = createRandomCard();
                if ((exceptCard != null) && (card.cardID == exceptCard))
                {
                    card = null;
                    continue;
                }
                
                if (upgraded)
                {
                    card.upgrade();
                }
                
                if (isTemporary)
                {
                    BcUtility.makeCardTemporary(card);
                }
                
                if (makeItFree)
                {
                    card.setCostForTurn(0);
                }
                
                BcUtility.addNewCardToHandOrDiscard(card);
            }
        }
        
        tickDuration();
    }
    
    AbstractCard createRandomCard()
    {
        AbstractCard card = BcUtility.getRandomCard(cardRarity, cardType, includeColorless, includeNative, includeForeign, allowHealing);
        
        return card.makeStatEquivalentCopy();
    }
}
