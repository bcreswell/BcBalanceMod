package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.powers.*;

public class UnnaturalRegeneration extends BcPowerCardBase
{
    public static final String ID = BcBalanceMod.makeID("UnnaturalRegeneration");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Unnatural Regeneration";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/unnaturalRegeneration.png";
    }
    
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.HEALING);
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
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }

    @Override
    public boolean getEthereal()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return "Sacrifice !M! HP. NL When you lose HP from a card, heal back that amount at the end of combat.";
        }
        else
        {
            return "When you lose HP from a card, heal back that amount at the end of combat. NL Sacrifice !M! HP.";
        }
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (!upgraded)
        {
            //doesn't heal back when lose hp applied first.
            addToBot(new LoseHPAction(player, player, magicNumber));
            addToBot(new BcApplyPowerAction(new UnnaturalRegenerationPower(player, 1)));
        }
        else
        {
            addToBot(new BcApplyPowerAction(new UnnaturalRegenerationPower(player, 1)));
            addToBot(new LoseHPAction(player, player, magicNumber));
        }
    }
}
