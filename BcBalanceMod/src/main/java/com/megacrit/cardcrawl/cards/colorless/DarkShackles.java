package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class DarkShackles extends BcSkillCardBase
{
    public static final String ID = "Dark Shackles";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/dark_shackles";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardTarget getCardTarget()
    {
        return CardTarget.ENEMY;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 9 : 15;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Temporarily reduce an enemy's Strength by !M!.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ApplyPowerAction(monster, player, new StrengthPower(monster, -magicNumber), -magicNumber));
        if ((monster != null) && !monster.hasPower("Artifact"))
        {
            addToBot(new ApplyPowerAction(monster, player, new GainStrengthPower(monster, magicNumber), magicNumber));
        }
    }
}
