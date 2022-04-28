//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.rewards.chests;

import basemod.BaseMod;
import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.BlightHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.relics.Matryoshka;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;
import com.megacrit.cardcrawl.relics.Sozu;

import java.util.ArrayList;
import java.util.Iterator;

public class BossChest extends AbstractChest
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public ArrayList<AbstractRelic> relics = new ArrayList();
    public ArrayList<AbstractBlight> blights = new ArrayList();
    
    public BossChest()
    {
        this.img = ImageMaster.BOSS_CHEST;
        this.openedImg = ImageMaster.BOSS_CHEST_OPEN;
        this.hb = new Hitbox(256.0F * Settings.scale, 200.0F * Settings.scale);
        this.hb.move(CHEST_LOC_X, CHEST_LOC_Y - 100.0F * Settings.scale);
        if (AbstractDungeon.actNum >= 4 && AbstractPlayer.customMods.contains("Blight Chests"))
        {
            this.blights.clear();
            this.blights.add(BlightHelper.getRandomBlight());
            ArrayList<String> exclusion = new ArrayList();
            exclusion.add(((AbstractBlight) this.blights.get(0)).blightID);
            this.blights.add(BlightHelper.getRandomChestBlight(exclusion));
        }
        else
        {
            this.relics.clear();
            
            relics.addAll(BcUtility.getRandomRelics(3, RelicTier.BOSS));
            relics.addAll(BcUtility.getRandomRelics(2, RelicTier.RARE));
        }
    }
    
    public void open(boolean bossChest)
    {
        if (AbstractDungeon.actNum >= 4 && AbstractPlayer.customMods.contains("Blight Chests"))
        {
            CardCrawlGame.sound.play("CHEST_OPEN");
            AbstractDungeon.bossRelicScreen.openBlight(this.blights);
        }
        else
        {
            Iterator var2 = AbstractDungeon.player.relics.iterator();
            
            while (var2.hasNext())
            {
                AbstractRelic r = (AbstractRelic) var2.next();
                if (!(r instanceof Matryoshka))
                {
                    r.onChestOpen(true);
                }
            }
            
            AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
            CardCrawlGame.sound.play("CHEST_OPEN");
            AbstractDungeon.bossRelicScreen.open(this.relics);
        }
        
    }
    
    public void close()
    {
        CardCrawlGame.sound.play("CHEST_OPEN");
        this.isOpen = false;
    }
    
    static
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("BossChest");
        TEXT = uiStrings.TEXT;
    }
}
