//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.*;

public class Overclock extends BcSkillCardBase
{
    public static final String ID = "Steam Power";
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Burn();
    }
    
    @Override
    public String getImagePath()
    {
        return "blue/skill/overclock";
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
        return !upgraded ? 3 : 4;
    }
    
    public int getBurnCount()
    {
        return !upgraded ? 3 : 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Double your energy. NL Draw !M! cards. NL Add " + getBurnCount() + " *Burns to your discard pile.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new VFXAction(player, new FlameBarrierEffect(player.hb.cX, player.hb.cY), 0.5F));
        addToBot(new DoubleEnergyAction());
        addToBot(new DrawCardAction(player, magicNumber, false));
        addToBot(new MakeTempCardInDiscardAction(new Burn(), getBurnCount()));
    }
}
