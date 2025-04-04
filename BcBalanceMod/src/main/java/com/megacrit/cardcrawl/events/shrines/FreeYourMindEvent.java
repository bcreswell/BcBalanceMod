package com.megacrit.cardcrawl.events.shrines;

import bcBalanceMod.BcBalanceMod;
import bcBalanceMod.BcUtility;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.FreeYourMind;
import com.megacrit.cardcrawl.cards.curses.Disbelief;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.curses.Fear;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.ArrayList;

public class FreeYourMindEvent extends AbstractImageEvent
{
    public static final String ID = BcBalanceMod.makeID("FreeYourMindEvent");

    private int screenNum;
    
    public FreeYourMindEvent()
    {
        super(
            "The Leap",
            "You have to let it all go Neo: #rFear, #rDoubt, and #rDisbelief. NL NL Free your mind.",
            "bcBalanceModResources/images/events/freeYourMind.png");
        
        this.screenNum = 1;
        this.imageEventText.setDialogOption("[Let it all go]");
        this.imageEventText.setDialogOption("\"Nah, there's no way.\"");
		AbstractDungeon.getCurrRoom().rewards.clear();
    }
    
    public void onEnterRoom()
    {
        CardCrawlGame.music.playTempBGM("SHRINE");
        BcUtility.markFreeYourMind();
    }
    
    @Override
    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
            case 1: //You have to Let it all go Neo
                switch(buttonPressed)
                {
                    case 0: //clicked [let it all go]
                        screenNum = 2;
                        imageEventText.updateBodyText(
                            "Okie Dokie. Free my mind. NL NL Right. No Problem. Free my mind. Free my mind. No Problem. Right...");
                            
                        imageEventText.loadImage("bcBalanceModResources/images/events/freeYourMind2.png");
                        
                        imageEventText.updateDialogOption(0, "[Free Your Mind]");
                        imageEventText.updateDialogOption(1, "\"I don't know about this.\"");
                        
                        ArrayList<AbstractCard> curses = new ArrayList<AbstractCard>();
                        
                        for(int i = 0; i < AbstractDungeon.player.masterDeck.group.size(); i++)
                        {
                            AbstractCard card = AbstractDungeon.player.masterDeck.group.get(i);
                            if ((card.cardID == Fear.ID) ||
                                (card.cardID == Doubt.ID) ||
                                (card.cardID == Disbelief.ID))
                            {
                                curses.add(card);
                            }
                        }
                        
                        if (!curses.isEmpty())
                        {
                            for(int i = 0; i < curses.size(); i++)
                            {
                                AbstractCard curse = curses.get(i);
                                
                                AbstractDungeon.effectList.add(new PurgeCardEffect(curse));
                                AbstractDungeon.player.masterDeck.removeCard(curse);
                            }
                        }
                        
                        break;
                    case 1: //Nah, there's no way
                        openMap();
                        break;
                }
                break;
            case 2: //Okie dokie. Free my mind.
                switch(buttonPressed)
                {
                    case 0: //clicked [free your mind]
                        screenNum = 3;
                        imageEventText.updateBodyText("");
                        imageEventText.loadImage("bcBalanceModResources/images/events/freeYourMind3.png");
                        imageEventText.updateDialogOption(0, "[...]");
                        imageEventText.clearRemainingOptions();
                        
                        //get the "Free Your Mind" card.
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FreeYourMind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        
                        break;
                    case 1: //I dont know about this
                        openMap();
                        break;
                }
                break;
//            case 3: //receive soar
//                 if (buttonPressed == 0)
//                {
//                    this.openMap();
//                }
//                if (buttonPressed == 0)
//                {
//                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new FreeYourMind(), (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
//                    screenNum = 4;
//
//                    imageEventText.updateBodyText("...");
//                    imageEventText.loadImage("bcBalanceModResources/images/events/freeYourMind4.png");
//                    imageEventText.updateDialogOption(0, "[Leave]");
//                    imageEventText.clearRemainingOptions();
//                }
                //break;
//            case 4: //leave
//                if (buttonPressed == 0)
//                {
//                    this.openMap();
//                }
//                break;
            default:
                if (buttonPressed == 0)
                {
                    this.openMap();
                }
                break;
        }
    }
}
