//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.blue;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import java.util.Iterator;

public class Blizzard extends BcAttackCardBase
{
    public static final String ID = "Blizzard";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "blue/attack/blizzard";
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
        return 2;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return true;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 6 : 10;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 2 : 3;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage to ALL enemies. NL +!M! Damage for each Frost Channeled this combat.";
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        int frostCount = getFrostChanneledThisCombat();

        return "Frost Count: "+frostCount;
    }
    //endregion
    
    int getFrostChanneledThisCombat()
    {
        int frostCount = 0;
        for (AbstractOrb orb : AbstractDungeon.actionManager.orbsChanneledThisCombat)
        {
            if (orb instanceof Frost)
            {
                frostCount++;
            }
        }
        
        return frostCount;
    }
    
    @Override
    public void applyPowers()
    {
        baseDamage = getBlizzDamage();
        calculateCardDamage(null);
        super.applyPowers();
    }
    
    int getBlizzDamage()
    {
        if (BcUtility.isPlayerInCombat())
        {
            return getDamage() + (getMagicNumber() * getFrostChanneledThisCombat());
        }
        else
        {
            return getDamage();
        }
    }
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        int frostCount = getFrostChanneledThisCombat();
        baseDamage = getBlizzDamage();
        calculateCardDamage(null);
    
        addToBot(new VFXAction(new BlizzardEffect(frostCount, AbstractDungeon.getMonsters().shouldFlipVfx()), .5f));
        addToBot(new DamageAllEnemiesAction(player, multiDamage, damageTypeForTurn, AttackEffect.BLUNT_HEAVY, false));
    }
}
