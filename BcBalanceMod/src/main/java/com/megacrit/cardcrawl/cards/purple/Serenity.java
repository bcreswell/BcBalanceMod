package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.powers.watcher.*;

public class Serenity extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Serenity");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Serenity";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/serenity.png";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 6 : 8;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public String getBaseDescription()
    {
        return applyConditionalHighlight(
            isPlayerInStance(CalmStance.STANCE_ID),
            "Gain !B! Block. NL Gain !M! Mantra. NL NL Calm: Repeat these effects.");
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        if (magicNumber > 0)
        {
            addToBot(new BcApplyPowerAction(new MantraPower(player, magicNumber)));
        }
        
        if (BcUtility.isPlayerInStance(CalmStance.STANCE_ID))
        {
            addToBot(new GainBlockAction(player, player, block));
            if (magicNumber > 0)
            {
                addToBot(new BcApplyPowerAction(new MantraPower(player, magicNumber)));
            }
        }
    }
}
