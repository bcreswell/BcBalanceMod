package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.BcAttackCardBase;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TrueWaitAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
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
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class Conclude extends BcAttackCardBase
{
    public static final String ID = "Conclude";
    
    //region card parameters
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/attack/conclude";
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
    public int getDamage()
    {
        return !upgraded ? 12 : 16;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return true;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage to ALL enemies. NL End your turn.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        AbstractDungeon.effectsQueue.add(new SpotlightPlayerEffect());
        addToBot(new TrueWaitAction(1.2f));
        addToBot(new SFXAction("ATTACK_HEAVY"));
        addToBot(new VFXAction(player, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AttackEffect.NONE));
        addToBot(new PressEndTurnButtonAction());
    }
}
