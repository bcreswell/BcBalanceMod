package com.megacrit.cardcrawl.cards.green;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.tempCards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

public class Reload extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("Reload");
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Shiv();
    }
    
    @Override
    public String getDisplayName()
    {
        return "Reload";
    }
    
    @Override
    public String getImagePath()
    {
        return "green/reload.png";
    }
    
    @Override
    public int getCost()
    {
        return -2;
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
    public int getMagicNumber()
    {
        return !upgraded ? 3 : 5;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Unplayable. NL When you discard this, gain !M! *Hidden Shivs.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
    }
    
    public boolean canUse(AbstractPlayer player, AbstractMonster monster)
    {
        this.cantUseMessage = "I can't play this card.";
        return false;
    }
    
    public void triggerOnManualDiscard()
    {
        AbstractPlayer player = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(player, player, new HiddenShivPower(player, magicNumber), magicNumber));
    }
}
