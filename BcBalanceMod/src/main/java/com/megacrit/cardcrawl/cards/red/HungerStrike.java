package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class HungerStrike extends BcAttackCardBase
{
    public static final String ID = BcBalanceMod.makeID("HungerStrike");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Hunger Strike";
    }
    
    @Override
    public String getImagePath()
    {
        return "red/theHunger.png";
    }
    
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.STRIKE);
        tags.add(AbstractCard.CardTags.HEALING);
    }
    
    @Override
    public int getCost()
    {
        return 2;
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
    public int getDamage()
    {
        return !upgraded ? 7 : 11;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. Heal for any unblocked damage. NL Can only be played against enemies that aren't attacking.";
    }
    
    boolean areAnyEnemiesAttackable()
    {
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            if (!monster.isDeadOrEscaped() &&
                (monster.intent != AbstractMonster.Intent.DEBUG) && //this can happen briefly at the start of combat
                (monster.getIntentBaseDmg() < 0))
            {
                return true;
            }
        }
        
        return false;
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        return areAnyEnemiesAttackable();
    }
    
    public boolean canUse(AbstractPlayer player, AbstractMonster monster)
    {
        if (!super.canUse(player, monster))
        {
            return false;
        }
        else
        {
            if ((monster == null) || (player == null))
            {
                return areAnyEnemiesAttackable();
            }
            
            if (monster.getIntentBaseDmg() >= 0)
            {
                cantUseMessage = "Can't, that Enemy is attacking.";
                return false;
            }
            else
            {
                return true;
            }
        }
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (monster != null)
        {
            addToBot(new VFXAction(new BiteEffect(monster.hb.cX, monster.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
            addToBot(new VampireDamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
//            addToBot(new VFXAction(new BiteEffect(monster.hb.cX, monster.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
//            addToBot(new VampireDamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
//        addToBot(new DamageAction(monster, new DamageInfo(player, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
//        addToBot(new HealAction(player, player, magicNumber));
    }
}
