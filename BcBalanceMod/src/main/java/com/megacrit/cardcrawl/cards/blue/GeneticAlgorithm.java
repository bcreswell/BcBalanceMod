package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

public class GeneticAlgorithm extends BcSkillCardBase
{
    public static final String ID = "Genetic Algorithm";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/skill/genetic_algorithm";
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
    protected void onInitialized()
    {
        //initial block
        misc = 1;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public int getBlock()
    {
        return misc;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL Retain your Block. NL Permanently increase this card's Block by !M!.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new BcApplyPowerAction(new BlurPower(player, 1)));
        addToBot(new IncreaseMiscAction(uuid, misc, magicNumber));
    }
    
    public void applyPowers()
    {
        baseBlock = misc;
        super.applyPowers();
        initializeDescription();
    }
}