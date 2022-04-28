//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import basemod.abstracts.CustomRelic;
import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import static bcBalanceMod.BcBalanceMod.makeRelicPath;

public class BcEctoplasm extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("BcEctoplasm");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("ectoplasm.png"));
    
    int previousGold = 0;
    
    public BcEctoplasm()
    {
        super(ID, "ectoplasm.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        return AbstractDungeon.player != null ? setDescription(AbstractDungeon.player.chosenClass) : setDescription(null);
    }
    
    private String setDescription(PlayerClass c)
    {
        return "Gain [E] at the start of your turn. NL When you try to pick up a #ygold reward, half of that reward disappears.";
    }
    
    public void updateDescription(PlayerClass c)
    {
        description = setDescription(c);
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }
    public void onLoseGold()
    {
        previousGold = AbstractDungeon.player.gold;
    }
    
    public void onGainGold()
    {
        int goldGained = AbstractDungeon.player.gold - previousGold;
        int goldThatDisappeared = goldGained / 2;
        
        CardCrawlGame.goldGained -= goldThatDisappeared;
        AbstractDungeon.player.gold -= goldThatDisappeared;
        
        previousGold = AbstractDungeon.player.gold;
    }
    
    public void onEquip()
    {
        previousGold = AbstractDungeon.player.gold;
        AbstractDungeon.player.energy.energyMaster++;
    }
    
    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster--;
    }
}
