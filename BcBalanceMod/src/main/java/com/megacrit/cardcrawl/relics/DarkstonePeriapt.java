package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class DarkstonePeriapt extends AbstractRelic
{
    public static final String ID = "Darkstone Periapt";
    private static final int HP_AMT = 6;
    
    public DarkstonePeriapt()
    {
        super("Darkstone Periapt", "darkstone.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.MAGICAL);
    }
    
    public void onObtainCard(AbstractCard card)
    {
        if (card.color == AbstractCard.CardColor.CURSE)
        {
            if (ModHelper.isModEnabled("Hoarder"))
            {
                AbstractDungeon.player.increaseMaxHp(6, true);
                AbstractDungeon.player.increaseMaxHp(6, true);
            }
            
            AbstractDungeon.player.increaseMaxHp(6, true);
        }
    }
    
    public void atBattleStart()
    {
        int curseCount = 0;
        for(AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.type == AbstractCard.CardType.CURSE)
            {
                curseCount++;
            }
        }
        
        if (curseCount > 0)
        {
            this.flash();
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, curseCount, 0.0F));
        }
    }
    
    public String getUpdatedDescription()
    {
        return "Whenever you obtain a #rCurse, increase your Max HP by #b" + HP_AMT + ". NL NL At the start of combat, heal #b1 HP for each #rCurse in your deck.";
    }
    
    public boolean canSpawn()
    {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }
    
    public AbstractRelic makeCopy()
    {
        return new DarkstonePeriapt();
    }
}
