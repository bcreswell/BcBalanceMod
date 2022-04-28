//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

public class BlackBlood extends AbstractRelic
{
    public static final String ID = "Black Blood";
    
    public BlackBlood()
    {
        super("Black Blood", "blackBlood.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        if (Settings.language == Settings.GameLanguage.ENG)
        {
            return "Replaces #rBurning #rBlood. NL At the end of combat, heal #b15 HP.";
        }
        else
        {
            return this.DESCRIPTIONS[0] + 12 + this.DESCRIPTIONS[1];
        }
    }
    
    public void onVictory()
    {
        this.flash();
        AbstractPlayer p = AbstractDungeon.player;
        this.addToTop(new RelicAboveCreatureAction(p, this));
        if (p.currentHealth > 0)
        {
            p.heal(15);
        }
    }
    
    public boolean canSpawn()
    {
        return AbstractDungeon.player.hasRelic("Burning Blood");
    }
    
    public AbstractRelic makeCopy()
    {
        return new BlackBlood();
    }
}
