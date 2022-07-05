//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.powers;

import bcBalanceMod.baseCards.*;
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

public class IfYouStrikeMeDownPower extends BcPowerBase
{
    public static final String POWER_ID = "IfYouStrikeMeDownPower";
    
    public IfYouStrikeMeDownPower(AbstractCreature owner, int amount)
    {
        super(owner, amount);
    }
    
    //region parameters
    @Override
    public String getDisplayName()
    {
        return "If You Strike Me Down";
    }
    
    @Override
    public String getId()
    {
        return POWER_ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "ifYouStrikeMeDown32x32.png";
    }
    
    @Override
    public PowerType getPowerType()
    {
        return PowerType.BUFF;
    }
    
    @Override
    public boolean getCanGoNegative()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "If you die: NL Return with " + IfYouStrikeMeDown.getHpToReviveWith() + " HP, NL Gain 1 Intangible, NL and on the next turn, Enter Divinity.";
    }
    //endregion
    
    public int onAttacked(DamageInfo info, int damageAmount)
    {
        AbstractPlayer player = AbstractDungeon.player;
        if ((damageAmount >= player.currentHealth) && (amount > 0))
        {
            amount--;
            player.currentHealth = 0;
            player.heal(IfYouStrikeMeDown.getHpToReviveWith(), true);
            
            addToTop(new BcApplyPowerAction(new IntangiblePlayerPower(player, 1)));
            addToTop(new BcApplyPowerAction(new DivinityNextTurnPower(player, 1)));
            addToTop(new VFXAction(player, new BorderLongFlashEffect(Color.RED), 0.0F, true));
            
            if (amount <= 0)
            {
                addToTop(new RemoveSpecificPowerAction(player, player, POWER_ID));
            }
            
            return 0;
        }
        
        return damageAmount;
    }
}
