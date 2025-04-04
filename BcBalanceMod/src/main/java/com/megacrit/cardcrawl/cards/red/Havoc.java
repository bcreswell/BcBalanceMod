//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.cards.red;

import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Havoc extends BcSkillCardBase {
    public static final String ID = "Havoc";

    //region card parameters
    @Override
    public String getImagePath()
    {
        return "red/skill/havoc";
    }

    @Override
    public int getCost()
    {
        return 0;
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
    public boolean getEthereal() { return true; }

    @Override
    public boolean getExhaust() { return !upgraded; }

    @Override
    public String getBaseDescription()
    {
        return "Play the top card of your draw pile and Exhaust it.";
    }
    //endregion

    public void use(AbstractPlayer player, AbstractMonster monster)
    {
        this.addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), true));
    }
}
