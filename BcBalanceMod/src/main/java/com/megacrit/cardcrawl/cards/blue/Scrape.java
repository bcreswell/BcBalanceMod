package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.ScrapeFollowUpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ScrapeEffect;

public class Scrape extends BcAttackCardBase
{
    public static final String ID = "Scrape";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/attack/scrape";
    }
    
    @Override
    public int getCost()
    {
        return 1;
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 5;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 7 : 11;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL Draw any zero cost cards from the next !M! cards of your draw pile.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (monster != null)
        {
            addToBot(new VFXAction(new ScrapeEffect(monster.hb.cX, monster.hb.cY), 0.1F));
        }
        
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new BcScrapeAction(magicNumber));
    }
}
