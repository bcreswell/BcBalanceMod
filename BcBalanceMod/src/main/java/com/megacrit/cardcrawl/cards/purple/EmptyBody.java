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

public class EmptyBody extends BcSkillCardBase
{
    public static final String ID = "EmptyBody";
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.EMPTY);
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/skill/empty_body";
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
        return CardRarity.UNCOMMON;
    }
    
    @Override
    public int getBlock()
    {
        return !upgraded ? 5 : 10;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Gain !B! Block. NL " + EmptyBlahAction.EmptyDescription;
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster m)
    {
        addToBot(new GainBlockAction(player, player, block));
        addToBot(new EmptyBlahAction());
    }
}
