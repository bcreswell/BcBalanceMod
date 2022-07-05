//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

public class EmptyFist extends BcAttackCardBase
{
    public static final String ID = "EmptyFist";
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.EMPTY);
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/attack/empty_fist";
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
    public CardRarity getCardRarity()
    {
        return CardRarity.COMMON;
    }
    
    @Override
    public int getDamage()
    {
        return !upgraded ? 9 : 13;
    }
    
    @Override
    public boolean isAoeAttack()
    {
        return false;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Deal !D! damage. NL " + EmptyBlahAction.EmptyDescription;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(player, damage, damageTypeForTurn), AttackEffect.BLUNT_LIGHT));
        EmptyBlahAction.preActionDraw();
        addToBot(new EmptyBlahAction());
    }
}
