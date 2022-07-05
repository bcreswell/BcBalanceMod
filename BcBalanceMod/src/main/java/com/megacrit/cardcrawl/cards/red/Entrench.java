//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class Entrench extends BcSkillCardBase
{
    public static final String ID = "Entrench";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/entrench";
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
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
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Double your Block.";
    }
    
    @Override
    public String getFootnote()
    {
        return "Doesn't trigger Juggernaut damage.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (!player.hasPower(NoBlockPower.POWER_ID))
        {
            AbstractPower juggernautPower = player.getPower(JuggernautPower.POWER_ID);
            if (juggernautPower != null)
            {
                //the block gain from entrench is just too much w/ juggernaut.
                ((JuggernautPower)juggernautPower).SkipNextBlockGain = true;
            }
            //addBlock instead of GainBlockAction to bypass frail
            player.addBlock(player.currentBlock);
            
            int ghostlyBlock = BcUtility.getPowerAmount(GhostlyBlockPower.POWER_ID);
            if (ghostlyBlock > 0)
            {
                //double ghostly block too
                addToBot(new BcApplyPowerAction(new GhostlyBlockPower(player, ghostlyBlock)));
            }
        }
    }
}
