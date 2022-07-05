package com.megacrit.cardcrawl.actions.common;

import bcBalanceMod.*;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DependencyInversionDrawAction extends AbstractGameAction
{
    public DependencyInversionDrawAction(int amount)
    {
        this.amount = amount;
    }
    
    public void update()
    {
        if (BcUtility.getCurrentFocus() < 0)
        {
            addToBot(new DrawCardAction(amount));
        }
        
        isDone = true;
    }
}
