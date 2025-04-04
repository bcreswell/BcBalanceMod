package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CorpseExplosionPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class CorpseExplosion extends BcSkillCardBase
{
    public static final String ID = "Corpse Explosion";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "green/skill/corpse_explosion";
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
        return CardRarity.RARE;
    }
    
    @Override
    public int getCost()
    {
        return 2;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 6 : 11;
    }
    
    @Override
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Apply !M! Poison. NL When the enemy dies, deal damage equal to its Max HP to ALL enemies.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(monster, player, new PoisonPower(monster, player, magicNumber), AbstractGameAction.AttackEffect.POISON, false));
        addToBot(new BcApplyPowerAction(monster, player, new CorpseExplosionPower(monster), AbstractGameAction.AttackEffect.POISON, false));
    }
}
