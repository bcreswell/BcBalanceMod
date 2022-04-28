package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class NinjaVanish extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("NinjaVanish");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Ninja Vanish!";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/ninjaVanish.png";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 0 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (getBlock() > 0)
        {
            return "Gain 1 Intangible NL and !B! Block. NL End your turn.";
        }
        else
        {
            return "Gain 1 Intangible. NL End your turn.";
        }
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new NinjaVanishEffect(player.hb.cX, player.hb.cY)));
        addToBot(new ApplyPowerAction(player, player, new IntangiblePlayerPower(player, 1), 1));
        if (upgraded)
        {
            addToBot(new GainBlockAction(player, block));
        }
        addToBot(new PressEndTurnButtonAction());
    }
}
