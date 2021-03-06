package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class Instruction extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Instruction");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Instruction";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/instruction.png";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 7 : 9;
    }
    
    public int getMantra()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Scry !M!. NL Gain " + getMantra() + " Mantra.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(new ThirdEyeEffect(player.hb.cX, player.hb.cY)));
        addToBot(new ScryAction(magicNumber));
        addToBot(new BcApplyPowerAction(new MantraPower(player, getMantra())));
    }
}
