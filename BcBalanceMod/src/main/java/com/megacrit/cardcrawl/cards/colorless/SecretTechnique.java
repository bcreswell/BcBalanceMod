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

public class SecretTechnique extends BcSkillCardBase
{
    public static final String ID = "Secret Technique";
    
    //region card parameters
    @Override
    public String getImagePath()
    {
        return "colorless/skill/secret_technique";
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
        return "Draw a Skill card of your choice.";
    }
    
    @Override
    public String getTemporaryExtraDescription(AbstractMonster monster)
    {
        AbstractPlayer player = AbstractDungeon.player;
        
        int skillCount = 0;
        for (AbstractCard card : player.drawPile.group)
        {
            if (card.type == CardType.SKILL)
            {
                skillCount++;
            }
        }
        
        if (skillCount == 1)
        {
            return "" + skillCount + " skill in draw pile";
        }
        else
        {
            return "" + skillCount + " skills in draw pile";
        }
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new DrawOfPlayersChoiceAction(magicNumber, false, false, true, false));
    }
}
