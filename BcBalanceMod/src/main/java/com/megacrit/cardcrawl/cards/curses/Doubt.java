package com.megacrit.cardcrawl.cards.curses;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Doubt extends AbstractCard {
    public static final String ID = "Doubt";
    private static final CardStrings cardStrings;
    
    public Doubt() {
        super("Doubt", cardStrings.NAME, "curse/doubt", -2, cardStrings.DESCRIPTION, AbstractCard.CardType.CURSE, AbstractCard.CardColor.CURSE, AbstractCard.CardRarity.CURSE, AbstractCard.CardTarget.NONE);
    }
    
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            this.addToTop(new BcApplyPowerAction(new WeakPower(AbstractDungeon.player, 1, true)));
        }
        
    }
    
    public void triggerWhenDrawn() {
        this.addToBot(new SetDontTriggerAction(this, false));
    }
    
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }
    
    public void upgrade() {
    }
    
    public AbstractCard makeCopy() {
        return new Doubt();
    }
    
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Doubt");
    }
}
