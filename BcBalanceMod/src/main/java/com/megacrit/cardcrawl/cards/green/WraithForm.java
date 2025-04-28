package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.WraithFormPower;

public class WraithForm extends BcPowerCardBase
{
    public static final String ID = "Wraith Form v2";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/power/wraith_form";
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
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public boolean getEthereal()
    {
        return !upgraded;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Become Intangible this turn and every 3rd turn for the rest of combat.";
    }
    
    @Override
    public String getFootnote()
    {
        return "Doesn't Stack.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new WraithFormPower(player, 1)));
    }
}
