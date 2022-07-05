package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.*;  import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class TwistFlameIntoFlower extends BcSkillCardBase
{
    public static final String ID = BcBalanceMod.makeID("TwistFlameIntoFlower");
    
    //region card parameters
    @Override
    public String getDisplayName()
    {
        return "Twist Flame into Flower";
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/twistFlameIntoFlower.png";
    }
    
    @Override
    protected void onInitialized()
    {
        tags.add(AbstractCard.CardTags.HEALING);
    }
    
    @Override
    public int getCost()
    {
        return !upgraded ? 1 : 0;
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
    public boolean getExhaust()
    {
        return true;
    }
    
    @Override
    public boolean getRetain()
    {
        return true;
    }
    
    @Override
    public int getMagicNumber()
    {
        return !upgraded ? 9 : 13;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Wrath: Heal for !M!. NL End your turn.";
    }
    //endregion
    
    @Override
    public boolean isGlowingGold()
    {
        return BcUtility.isPlayerInStance(WrathStance.STANCE_ID);
    }
    
    @Override
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        if (BcUtility.isPlayerInStance(WrathStance.STANCE_ID))
        {
            addToBot(new VFXAction(player, new InflameEffect(player), 1.0F));
            addToBot(new HealAction(player, player, magicNumber));
        }
        addToBot(new PressEndTurnButtonAction());
    }
}
