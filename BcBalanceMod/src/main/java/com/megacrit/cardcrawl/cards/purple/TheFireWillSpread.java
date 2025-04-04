package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class TheFireWillSpread extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("TheFireWillSpread");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "The Fire Will Spread";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/theFireWillSpread.png";
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
    public int getMagicNumber()
    {
        return 3;
    }
    
    public int getScryBonus()
    {
        return !upgraded ? 2 : 4;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "+" + getScryBonus() + " to ALL Scrying. NL When you Scry, enemies are randomly struck for that much damage, !M! times.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
        addToBot(new VFXAction(player, new InflameEffect(player), 1.0F));
        addToBot(new BcApplyPowerAction(new ForesightPower(player, getScryBonus())));
        addToBot(new BcApplyPowerAction(new TheFireWillSpreadPower(player, magicNumber)));
    }
}
