package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.CastOrbAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.*;

public class CastToLightning extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("CastToLightning");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Cast to Lightning";
    }
    
    @Override
    public CardColor getCardColor()
    {
        return CardColor.COLORLESS;
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/zap";
    }
    
    @Override
    public int getCost()
    {
        return -2;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public AbstractCard.CardRarity getCardRarity()
    {
        return CardRarity.SPECIAL;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Convert all Orbs to Lightning Orbs.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        onChoseThisOption();
    }
    
    public void onChoseThisOption()
    {
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); ++i)
        {
            addToBot(new CastOrbAction(i, new Lightning()));
        }
    }
}