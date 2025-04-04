package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.*;
import bcBalanceMod.baseCards.*;
import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.vfx.RainbowCardEffect;
import java.util.*;

public class TypeCast extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("TypeCast");
    public static final int LargeDarkThreshold = 30;
    
    CastToDark castToDark;
    CastToLightning castToLightning;
    CastToFrost castToFrost;
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Type Cast";
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/typeCast.png";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    protected void onInitialized()
    {
        castToDark = new CastToDark();
        castToLightning = new CastToLightning();
        castToFrost = new CastToFrost();
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
    public boolean getExhaust()
    {
        return !upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        //return "Choose One: NL Dark, Lightning or Frost. NL NL Convert all Orbs, except the first one, to the chosen type.";
        return "Convert your Orbs to Dark, Lightning or Frost. NL NL Doesn't convert Plasma or large ("+LargeDarkThreshold+" mass) Dark Orbs.";
    }
    //endregion
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        ArrayList<AbstractCard> choices = new ArrayList();
        
        choices.add(castToDark.makeStatEquivalentCopy());
        choices.add(castToLightning.makeStatEquivalentCopy());
        choices.add(castToFrost.makeStatEquivalentCopy());
        
        addToBot(new ChooseOneAction(choices));
    }
}