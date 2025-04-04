//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PerfectedStrike extends BcAttackCardBase
{
    public static final String ID = "Perfected Strike";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/attack/perfected_strike";
    }
    
    @Override
    protected void onInitialized()
    {
        tags.add(CardTags.STRIKE);
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
    public int getDamage()
    {
        return 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 4 : 5;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        int strikeCount = getStrikeCount();
        if (BcUtility.isCombatCard(this))
        {
            return "Deal !D! damage. NL Deals !M! damage for each of your cards containing \"Strike\".";
        }
        else if (strikeCount > 0)
        {
            return "Deals damage equal to NL !M!x the number of your cards containing \"Strike\". NL ("+strikeCount+" strikes)";
        }
        else
        {
            return "Deals damage equal to NL !M!x the number of your cards containing \"Strike\".";
        }
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        int strikeCount = getStrikeCount();
        return "" + strikeCount + " strikes";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL));
    }
    
    int getStrikeCount()
    {
        int count = 0;
        
        if (BcUtility.isPlayerInCombat())
        {
            //region in combat
            AbstractPlayer player = AbstractDungeon.player;
            for (AbstractCard c : player.hand.group)
            {
                if (c.hasTag(CardTags.STRIKE))
                {
                    count++;
                }
            }
            
            for (AbstractCard c : player.drawPile.group)
            {
                if (c.hasTag(CardTags.STRIKE))
                {
                    count++;
                }
            }
            
            for (AbstractCard c : player.discardPile.group)
            {
                if (c.hasTag(CardTags.STRIKE))
                {
                    count++;
                }
            }
            
            for (AbstractCard c : player.exhaustPile.group)
            {
                if (c.hasTag(CardTags.STRIKE))
                {
                    count++;
                }
            }
            //endregion
        }
        else if ((AbstractDungeon.player != null) && (AbstractDungeon.player.masterDeck != null))
        {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            {
                if (c.hasTag(CardTags.STRIKE))
                {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    int getFinalBaseDamage()
    {
        return getDamage() + getStrikeCount() * getMagicNumber();
    }
    
    @Override
    public void applyPowers()
    {
        baseDamage = getFinalBaseDamage();
        calculateCardDamage(null);
        super.applyPowers();
    }
}
