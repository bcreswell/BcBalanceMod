//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

public class DodgeAndRoll extends BcSkillCardBase
{
    public static final String ID = "Dodge and Roll";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Dodge and Roll";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/skill/dodge_and_roll";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 6 : 8;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block and NL Discard 1 card. NL #gNext #gTurn: NL Gain !B! Block and NL Draw a card.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster) 
    {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new DiscardAction(player, player, 1, false));
        
        addToBot(new BcApplyPowerAction(new NextTurnBlockPower(player, block)));
        addToBot(new BcApplyPowerAction(new DrawCardNextTurnPower(player, 1)));
    }
}