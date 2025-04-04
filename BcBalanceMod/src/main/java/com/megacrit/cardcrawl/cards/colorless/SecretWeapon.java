package com.megacrit.cardcrawl.cards.colorless;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.unique.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class SecretWeapon extends BcSkillCardBase
{
    public static final String ID = "Secret Weapon";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/secret_weapon";
    }
    
    @Override
    public String getId()
    {
        return ID;
    }
    
    @Override
    public CardRarity getCardRarity()
    {
        return CardRarity.RARE;
    }
    
    @Override
    public int getCost()
    {
        return 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return 1;
    }
    
    @Override
    public boolean getExhaust()
    {
        return !upgraded;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Pick an Attack card to Draw.";
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        int attackCount = 0;
        for (AbstractCard card : player.drawPile.group)
        {
            if (card.type == CardType.ATTACK)
            {
                attackCount++;
            }
        }
    
        if (attackCount == 1)
        {
            return "" + attackCount + " attack in draw pile";
        }
        else
        {
            return "" + attackCount + " attacks in draw pile";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DrawOfPlayersChoiceAction(magicNumber, false, false, false, true));
    }
}
