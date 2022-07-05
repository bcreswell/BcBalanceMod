//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Vigilance extends BcSkillCardBase
{
    public static final String ID = "Vigilance";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "purple/skill/vigilance";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.BASIC;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 10 : 16;
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        //changed the order for the sake of new Wave of the Hand.
        return "Enter Calm. NL Gain !B! Block.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new ChangeStanceAction("Calm"));
        addToBot(new GainBlockAction(player, block));
    }
}
