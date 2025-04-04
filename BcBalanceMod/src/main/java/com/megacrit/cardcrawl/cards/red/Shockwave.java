package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.BcSkillCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.BcApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import java.util.Iterator;

public class Shockwave extends BcSkillCardBase
{
    public static final String ID = "Shockwave";
    
    //region Description
    @Override
    public String getImagePath()
    {
        return "red/skill/shockwave";
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
    public int getCost()
    {
        return !upgraded ? 2 : 1;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 4;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Inflict !M! Weak and Vulnerable on ALL enemies.";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster m)
    {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            addToBot(new BcApplyPowerAction(monster, new WeakPower(monster, magicNumber, false)));
            addToBot(new BcApplyPowerAction(monster, new VulnerablePower(monster, magicNumber, false)));
        }
    }
}
