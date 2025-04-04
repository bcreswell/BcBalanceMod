package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

public class EmptySpirit extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("EmptySpirit");
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.EMPTY);
    }
    
    @Override
    public String getDisplayName()
    {
        return "Empty Spirit";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/emptySpirit.png";
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
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public boolean getRetain()
    {
        return upgraded;
    }

    @Override
    public String getBaseDescription()
    {
        if (!upgraded)
        {
            return EmptyBlahAction.GetEmptyDescription();
        }
        else
        {
            //newline after "Retain"
            return " NL "+EmptyBlahAction.GetEmptyDescription();
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        EmptyBlahAction.preActionDraw();
        addToBot(new EmptyBlahAction());
    }
}
