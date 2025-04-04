//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PowerThrough extends BcSkillCardBase
{
    public static final String ID = "Power Through";
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Power Through";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/skill/power_through";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Wound();
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
        return !upgraded ? 15 : 20;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL Shuffle 2 *Wounds into your draw pile.";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new GainBlockAction(player, player, block));
        //addToBot(new MakeTempCardInHandAction(new Wound(), 2));
        addToBot(new MakeTempCardInDrawPileAction(cardsToPreview, 2, true, true));
    }
}
