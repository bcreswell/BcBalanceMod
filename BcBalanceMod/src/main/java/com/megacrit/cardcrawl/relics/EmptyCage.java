package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class EmptyCage extends AbstractRelic
{
    public static final String ID = "Empty Cage";
    private boolean cardsSelected = true;

    public static final int CardCountToRemove = 2;

    public EmptyCage()
    {
        super("Empty Cage", "cage.png", RelicTier.RARE, AbstractRelic.LandingSound.SOLID);
    }
    
    public String getUpdatedDescription()
    {
        if (CardCountToRemove == 1)
        {
            return "Remove a card from your deck.";
        }
        else
        {
            return "Remove " + CardCountToRemove + " cards from your deck.";
        }
    }
    
    public void onEquip()
    {
        this.cardsSelected = false;
        if (AbstractDungeon.isScreenUp)
        {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var2 = AbstractDungeon.player.masterDeck.getPurgeableCards().group.iterator();
        
        while (var2.hasNext())
        {
            AbstractCard card = (AbstractCard) var2.next();
            tmp.addToTop(card);
        }
        
        if (tmp.group.isEmpty())
        {
            this.cardsSelected = true;
        }
        else
        {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), CardCountToRemove, this.DESCRIPTIONS[1], false, false, true, true);
        }
    }
    
    public void update()
    {
        super.update();
        if (!this.cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == CardCountToRemove)
        {
            this.deleteCards(AbstractDungeon.gridSelectScreen.selectedCards);
        }
    }
    
    public void deleteCards(ArrayList<AbstractCard> group)
    {
        this.cardsSelected = true;
        float displayCount = 0.0F;
        Iterator i = group.iterator();
        
        while (i.hasNext())
        {
            AbstractCard card = (AbstractCard) i.next();
            card.untip();
            card.unhover();
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (float) Settings.WIDTH / 3.0F + displayCount, (float) Settings.HEIGHT / 2.0F));
            displayCount += (float) Settings.WIDTH / 6.0F;
            AbstractDungeon.player.masterDeck.removeCard(card);
        }
        
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
    }
    
    public AbstractRelic makeCopy()
    {
        return new EmptyCage();
    }
}
