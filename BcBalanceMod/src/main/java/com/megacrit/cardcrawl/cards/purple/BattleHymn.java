package com.megacrit.cardcrawl.cards.purple;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.BattleHymnPower;

public class BattleHymn extends BcPowerCardBase
{
    public static final String ID = "BattleHymn";
    public static final int AttacksPerSmite = 3;
    
    //region card parameters
    @Override
    protected void onInitialized()
    {
        cardsToPreview = new Smite();
    }
    
    @Override
    public String getImagePath()
    {
        return "purple/power/battle_hymn";
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
        return !upgraded ? 1 : 0;
    }
    
    @Override
    public int getMagicNumber()
    {
        return AttacksPerSmite;
    }
    
    @Override
    public String getBaseDescription()
    {
        return "Once per turn: NL After you play !M! Attacks in a turn, create a zero cost Smite.";
    }
    //endregion
    
    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        addToBot(new BcApplyPowerAction(new BattleHymnPower(player, 1)));
    }
}
