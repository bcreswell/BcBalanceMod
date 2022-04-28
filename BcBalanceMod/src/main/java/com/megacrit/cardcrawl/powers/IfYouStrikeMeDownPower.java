//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.util.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class IfYouStrikeMeDownPower extends AbstractPower
{
    public static final String POWER_ID = "IfYouStrikeMeDownPower";
    
    private static final Texture smallIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/ifYouStrikeMeDown35x35.png");
    private static final Texture largeIcon = TextureLoader.getTexture("bcBalanceModResources/images/powers/ifYouStrikeMeDown84x84.png");
    
    public IfYouStrikeMeDownPower(AbstractCreature owner, int amount)
    {
        name = "If You Strike Me Down";
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.region48 = new TextureAtlas.AtlasRegion(smallIcon, 0, 0, smallIcon.getWidth(), smallIcon.getHeight());
        this.region128 = new TextureAtlas.AtlasRegion(largeIcon, 0, 0, largeIcon.getWidth(), largeIcon.getHeight());
    }
    
    public void updateDescription()
    {
        description = "If you die: NL Return with " + IfYouStrikeMeDown.getHpToReviveWith() + " HP, NL Gain 1 Intangible, NL and on the next turn, Enter Divinity.";
    }
    
    public int onAttacked(DamageInfo info, int damageAmount)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if ((damageAmount >= player.currentHealth) && (amount > 0))
        {
            amount--;
            player.currentHealth = 0;
            player.heal(IfYouStrikeMeDown.getHpToReviveWith(), true);
            
            addToTop(new ApplyPowerAction(player, player, new IntangiblePlayerPower(player, 1), 1));
            addToTop(new ApplyPowerAction(player, player, new DivinityNextTurnPower(player, 1), 1));
            addToTop(new VFXAction(player, new BorderLongFlashEffect(Color.RED), 0.0F, true));
            
//            addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
//            addToBot(new NotStanceCheckAction(NeutralStance.STANCE_ID, new VFXAction(new EmptyStanceEffect(player.hb.cX, player.hb.cY), 0.1F)));
//            addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));
            
            if (amount <= 0)
            {
                addToTop(new RemoveSpecificPowerAction(player, player, POWER_ID));
            }
            
            return 0;
        }
        
        return damageAmount;
    }
}
