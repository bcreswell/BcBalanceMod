package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.*;

public class BurningPact extends BcSkillCardBase
{
    public static final String ID = "Burning Pact";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/burning_pact";
    }
    
    @Override
    public int getCost()
    {
        return 0;
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
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 4;
    }
    
    public int getHpLoss()
    {
        return 2;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Sacrifice " + getHpLoss() + " HP. NL Exhaust a card. NL Draw !M! cards.";
    }
    
    @Override
    public String getFootnote()
    {
        return "Doesn't draw if it NL lacks a target card.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        //does nothing if there's no target to exhaust
        if (player.hand.size() >= 2)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(player.hb.cX, player.hb.cY, AbstractGameAction.AttackEffect.FIRE, false));
            addToBot(new LoseHPAction(player, player, getHpLoss()));
            addToBot(new TrueWaitAction(.2f));
            addToBot(new ExhaustAction(1, false));
            addToBot(new DrawCardAction(player, magicNumber));
        }
    }
}
