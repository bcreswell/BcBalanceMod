package com.megacrit.cardcrawl.actions.utility;

import bcBalanceMod.*;
import bcBalanceMod.baseCards.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.relics.*;

public class ScryAction extends AbstractGameAction
{
    CardGroup scryGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private float startingDuration;
    boolean scryFinished;
    boolean firstUpdate;
    int dizzyToApply = 0;
    public static int LatestScryAmount = 0;
    
    public ScryAction(int numCards)
    {
        amount = numCards + BcUtility.getPowerAmount(ForesightPower.POWER_ID);
        if (AbstractDungeon.player.hasRelic("GoldenEye"))
        {
            AbstractDungeon.player.getRelic("GoldenEye").flash();
            amount += 2;
        }
        LatestScryAmount = amount;
        
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        startingDuration = Settings.ACTION_DUR_FAST;
        duration = startingDuration;
        scryFinished = false;
        firstUpdate = true;
    }
    
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead())
        {
            isDone = true;
        }
        else
        {
            if (firstUpdate)
            {
                firstUpdate = false;
                dizzyToApply = 0;
                
                //region first update
                //scry can shuffle discard pile into draw pile if needed.
                if (amount > player.drawPile.size())
                {
                    dizzyToApply = DizzyPower.DizzyPerShuffle - player.drawPile.size();
                    if (dizzyToApply < 0)
                    {
                        dizzyToApply = 0;
                    }
//                  BcBalancingScales scales = (BcBalancingScales) player.getRelic(BcBalancingScales.ID);
//                  if (scales != null)
//                  {
//                      scales.onSpecialScryShuffle();
//
                    
                    //this is a weird shuffle algorithm. Wanted to shuffle only the discard pile part and leave the draw pile in it's current order
                    //the reason: if you can see that 1 card in your draw pile is an important one, dont want to lose track of it because you scryed
                    //so we randomize the order of the discard pile but put it underneath the existing draw pile without disturbing their order.
                    while (!player.discardPile.isEmpty())
                    {
                        AbstractCard card = player.discardPile.getRandomCard(true);
                        //'false' here moves the card to the top of the deck,
                        player.discardPile.moveToDeck(card, false);
                        // but should be bottom instead, so we gotta monkey with it
                        card = player.drawPile.group.remove(player.drawPile.group.size() - 1);
                        player.drawPile.addToBottom(card);
                    }
                }
                
                amount = Math.min(amount, player.drawPile.size());
                
                for (int i = 0; i < amount; i++)
                {
                    AbstractCard card = player.drawPile.group.get((player.drawPile.group.size() - i) - 1);
                    scryGroup.addToBottom(card);
                }
                
                if (scryGroup.size() > 0)
                {
                    AbstractDungeon.gridSelectScreen.open(scryGroup, amount, true, "Choose any number of cards to discard.", false);
                }
                else
                {
                    isDone = true;
                }
                //endregion
            }
            else if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.GRID)
            {
                if (!scryFinished)
                {
                    scryFinished = true;
                    
                    if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
                    {
                        //discarding cards with scry should be treated as if they hit the hand momentarily before being discarded.
                        //this is to prevent scry heavy builds from always instantly getting nauseous from dizzy.
                        dizzyToApply -= AbstractDungeon.gridSelectScreen.selectedCards.size();

                        if (dizzyToApply > 0)
                        {
                            BcBalancingScales scales = (BcBalancingScales) player.getRelic(BcBalancingScales.ID);
                            if (scales != null) {
                                scales.applyDizzy(dizzyToApply);
                            }
                        }
                        else if (dizzyToApply < 0)
                        {
                            AbstractPower pow = player.getPower(DizzyPower.POWER_ID);
                            if (pow != null)
                            {
                                DizzyPower dizzy = (DizzyPower)pow;

                                dizzy.reducePower(dizzyToApply * -1);
                                if (dizzy.amount == 0)
                                {
                                    addToBot(new RemoveSpecificPowerAction(player, player, DizzyPower.POWER_ID));
                                }
                            }
                        }
                        dizzyToApply = 0;

                        //region after player has made scry selections
                        for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards)
                        {
                            player.drawPile.moveToDiscardPile(card);
                        }
                        AbstractDungeon.gridSelectScreen.selectedCards.clear();
                    }
                    
                    for (AbstractCard card : player.drawPile.group)
                    {
                        card.triggerOnScry();
                    }
                    
                    for (AbstractCard card : player.discardPile.group)
                    {
                        card.triggerOnScry();
                    }
                    
                    for (AbstractPower power : player.powers)
                    {
                        if (power instanceof BcPowerBase)
                        {
                            ((BcPowerBase) power).onScry(amount);
                        }
                        power.onScry();
                    }
                }
                else
                {
                    tickDuration();
                }
            }
        }
    }
}
