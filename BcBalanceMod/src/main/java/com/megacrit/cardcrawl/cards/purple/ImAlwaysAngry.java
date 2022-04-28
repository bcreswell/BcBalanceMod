package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class ImAlwaysAngry extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("ImAlwaysAngry");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "  I'm Always Angry";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/imAlwaysAngry.png";
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 7 : 10;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 7 : 10;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.ENEMY;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Gain !B! Block. NL Wrath: Gain [W] . NL Otherwise: NL Enter Wrath.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
//
//        addToBot(new WaitAction(0.1F));
//        addToBot(new VFXAction(new IronWaveEffect(player.hb.cX, player.hb.cY, monster.hb.cX), 0.5F));
        
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        
        if (player.stance.ID.equals("Wrath"))
        {
            addToBot(new GainEnergyAction(1));
        }
        else
        {
            this.addToBot(new ChangeStanceAction("Wrath"));
        }
    }
}
