package com.megacrit.cardcrawl.events.city;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class CursedTome extends AbstractImageEvent
{
    public static final String ID = "Cursed Tome";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final String INTRO_MSG;
    private static final String READ_1;
    private static final String READ_2;
    private static final String READ_3;
    private static final String READ_4;
    private static final String OBTAIN_MSG;
    private static final String IGNORE_MSG;
    private static final String STOP_MSG;
    private static final String OPT_READ;
    private static final String OPT_CONTINUE_1;
    private static final String OPT_CONTINUE_2;
    private static final String OPT_CONTINUE_3;
    private static final String OPT_STOP;
    private static final String OPT_LEAVE;
    private static final int DMG_BOOK_OPEN = 1;
    private static final int DMG_SECOND_PAGE = 2;
    private static final int DMG_THIRD_PAGE = 3;
    private static final int DMG_STOP_READING = 3;
    private static final int DMG_OBTAIN_BOOK = 10;
    private static final int A_2_DMG_OBTAIN_BOOK = 15;
    private int finalDmg;
    private int damageTaken;
    private CursedTome.CurScreen screen;
    AbstractRelic theBook;
    
    public CursedTome()
    {
        super(NAME, INTRO_MSG, "images/events/cursedTome.jpg");
        screen = CursedTome.CurScreen.INTRO;
        noCardsInRewards = true;
        damageTaken = 0;
        if (AbstractDungeon.ascensionLevel >= 15)
        {
            finalDmg = A_2_DMG_OBTAIN_BOOK;
        }
        else
        {
            finalDmg = DMG_OBTAIN_BOOK;
        }
        
        imageEventText.setDialogOption(OPT_READ);
        imageEventText.setDialogOption(OPT_LEAVE);
    }
    
    protected void buttonEffect(int buttonPressed)
    {
        switch (screen)
        {
            case INTRO:
                imageEventText.clearAllDialogs();
                if (buttonPressed == 0)
                {
                    CardCrawlGame.sound.play("EVENT_TOME");
                    imageEventText.clearAllDialogs();
                    imageEventText.setDialogOption(OPT_CONTINUE_1);
                    imageEventText.updateBodyText(READ_1);
                    screen = CursedTome.CurScreen.PAGE_1;
                }
                else
                {
                    imageEventText.clearAllDialogs();
                    imageEventText.setDialogOption(OPT_LEAVE);
                    imageEventText.updateBodyText(IGNORE_MSG);
                    screen = CursedTome.CurScreen.END;
                    logMetricIgnored("Cursed Tome");
                }
                break;
            case PAGE_1:
                CardCrawlGame.sound.play("EVENT_TOME");
                AbstractDungeon.player.damage(new DamageInfo(null, DMG_BOOK_OPEN, DamageInfo.DamageType.HP_LOSS));
                damageTaken += DMG_BOOK_OPEN;
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPT_CONTINUE_2);
                imageEventText.updateBodyText(READ_2);
                screen = CursedTome.CurScreen.PAGE_2;
                break;
            case PAGE_2:
                CardCrawlGame.sound.play("EVENT_TOME");
                AbstractDungeon.player.damage(new DamageInfo(null, DMG_SECOND_PAGE, DamageInfo.DamageType.HP_LOSS));
                damageTaken += DMG_SECOND_PAGE;
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPT_CONTINUE_3);
                imageEventText.updateBodyText(READ_3);
                screen = CursedTome.CurScreen.PAGE_3;
                break;
            case PAGE_3:
                CardCrawlGame.sound.play("EVENT_TOME");
                AbstractDungeon.player.damage(new DamageInfo(null, DMG_THIRD_PAGE, DamageInfo.DamageType.HP_LOSS));
                damageTaken += DMG_THIRD_PAGE;
                theBook = getRandomBook();
                imageEventText.clearAllDialogs();
                imageEventText.setDialogOption(OPTIONS[5] + finalDmg + OPTIONS[6], theBook);
                imageEventText.setDialogOption(OPT_STOP);
                imageEventText.updateBodyText(READ_4);
                screen = CursedTome.CurScreen.LAST_PAGE;
                break;
            case LAST_PAGE:
                if (buttonPressed == 0)
                {
                    AbstractDungeon.player.damage(new DamageInfo(null, finalDmg, DamageInfo.DamageType.HP_LOSS));
                    damageTaken += finalDmg;
                    imageEventText.updateBodyText(OBTAIN_MSG);
                    randomBook();
                    imageEventText.clearAllDialogs();
                    imageEventText.setDialogOption(OPT_LEAVE);
                }
                else
                {
                    AbstractDungeon.player.damage(new DamageInfo(null, DMG_STOP_READING, DamageInfo.DamageType.HP_LOSS));
                    damageTaken += DMG_STOP_READING;
                    imageEventText.updateBodyText(STOP_MSG);
                    logMetricTakeDamage("Cursed Tome", "Stopped", damageTaken);
                    imageEventText.clearAllDialogs();
                    imageEventText.setDialogOption(OPT_LEAVE);
                    screen = CursedTome.CurScreen.END;
                }
                break;
            case END:
                imageEventText.updateDialogOption(0, OPT_LEAVE);
                imageEventText.clearRemainingOptions();
                openMap();
        }
        
    }
    
    AbstractRelic getRandomBook()
    {
        ArrayList<AbstractRelic> possibleBooks = new ArrayList();
        if (!AbstractDungeon.player.hasRelic("Necronomicon"))
        {
            possibleBooks.add(RelicLibrary.getRelic("Necronomicon").makeCopy());
        }
    
        if (!AbstractDungeon.player.hasRelic("Enchiridion"))
        {
            possibleBooks.add(RelicLibrary.getRelic("Enchiridion").makeCopy());
        }
    
        if (!AbstractDungeon.player.hasRelic("Nilry's Codex"))
        {
            possibleBooks.add(RelicLibrary.getRelic("Nilry's Codex").makeCopy());
        }
    
        if (possibleBooks.size() == 0)
        {
            possibleBooks.add(RelicLibrary.getRelic("Circlet").makeCopy());
        }
    
        return possibleBooks.get(AbstractDungeon.miscRng.random(possibleBooks.size() - 1));
    }
    
    private void randomBook()
    {
//        ArrayList<AbstractRelic> possibleBooks = new ArrayList();
//        if (!AbstractDungeon.player.hasRelic("Necronomicon"))
//        {
//            possibleBooks.add(RelicLibrary.getRelic("Necronomicon").makeCopy());
//        }
//
//        if (!AbstractDungeon.player.hasRelic("Enchiridion"))
//        {
//            possibleBooks.add(RelicLibrary.getRelic("Enchiridion").makeCopy());
//        }
//
//        if (!AbstractDungeon.player.hasRelic("Nilry's Codex"))
//        {
//            possibleBooks.add(RelicLibrary.getRelic("Nilry's Codex").makeCopy());
//        }
//
//        if (possibleBooks.size() == 0)
//        {
//            possibleBooks.add(RelicLibrary.getRelic("Circlet").makeCopy());
//        }
//
//        AbstractRelic r = (AbstractRelic) possibleBooks.get(AbstractDungeon.miscRng.random(possibleBooks.size() - 1));
        
        logMetricTakeDamage("Cursed Tome", "Obtained Book", damageTaken);
        AbstractDungeon.getCurrRoom().rewards.clear();
        AbstractDungeon.getCurrRoom().addRelicToRewards(theBook);
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.combatRewardScreen.open();
        screen = CursedTome.CurScreen.END;
    }
    
    static
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Cursed Tome");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_MSG = DESCRIPTIONS[0];
        READ_1 = DESCRIPTIONS[1];
        READ_2 = DESCRIPTIONS[2];
        READ_3 = DESCRIPTIONS[3];
        READ_4 = DESCRIPTIONS[4];
        OBTAIN_MSG = DESCRIPTIONS[5];
        IGNORE_MSG = DESCRIPTIONS[6];
        STOP_MSG = DESCRIPTIONS[7];
        OPT_READ = OPTIONS[0];
        OPT_CONTINUE_1 = OPTIONS[1];
        OPT_CONTINUE_2 = OPTIONS[2];
        OPT_CONTINUE_3 = OPTIONS[3];
        OPT_STOP = OPTIONS[4];
        OPT_LEAVE = OPTIONS[7];
    }
    
    private static enum CurScreen
    {
        INTRO,
        PAGE_1,
        PAGE_2,
        PAGE_3,
        LAST_PAGE,
        END;
        
        private CurScreen()
        {
        }
    }
}
