//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.relics;

import basemod.abstracts.CustomRelic;
import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import static bcBalanceMod.BcBalanceMod.makeRelicPath;

public class BcSozu extends CustomRelic
{
    public static final String ID = BcBalanceMod.makeID("BcSozu");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("sozu.png"));
    
    //making it a special relic as a way to disable it
    public BcSozu()
    {
        super(ID, "sozu.png", RelicTier.BOSS, LandingSound.FLAT);
    }
    
    public String getUpdatedDescription()
    {
        return AbstractDungeon.player != null ? this.setDescription(AbstractDungeon.player.chosenClass) : this.setDescription((PlayerClass) null);
    }
    
    private String setDescription(PlayerClass c)
    {
        return "Gain [E] at the start of your turn. NL Lose 1 potion slot.";
    }
    
    public void updateDescription(PlayerClass c)
    {
        this.description = this.setDescription(c);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    
    public void onEquip()
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        //shift potions left to let player keep their potions if possible when losing a slot
        for (int i = 1; i < player.potionSlots; i++)
        {
            if ((player.potions.get(i - 1) instanceof PotionSlot) &&
                        !(player.potions.get(i) instanceof PotionSlot))
            {
                AbstractPotion potion = player.potions.get(i);
                player.potions.set(i, new PotionSlot(i));
                player.potions.set(i - 1, potion);
                potion.setAsObtained(i - 1);
            }
        }
        
        player.energy.energyMaster++;
        player.potionSlots--;
        
        AbstractDungeon.player.potions.remove(AbstractDungeon.player.potionSlots);
    }
    
    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster--;
        AbstractDungeon.player.potionSlots++;
        AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));
    }
}
