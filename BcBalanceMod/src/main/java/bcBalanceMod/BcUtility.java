package bcBalanceMod;

import basemod.*;
import bcBalanceMod.baseCards.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Disbelief;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.curses.Fear;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.stances.CalmStance;
import com.megacrit.cardcrawl.stances.DivinityStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.stances.WrathStance;
import com.megacrit.cardcrawl.ui.panels.*;
import com.megacrit.cardcrawl.unlock.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.vfx.combat.EndTurnEarlyEffect;
import org.apache.logging.log4j.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class BcUtility
{
    //region gameplay constants
    public static final int RitualCurseRemovalSacrifice = 6;
    static final int InitialRemovalCost = 75;
    static final int InitialRemovalCostA16 = 85;
    static final int RemovalCostIncrement = 25;
    static final int RemovalCostIncrementA16 = 30;
    //endregion
    
    public static boolean LogCardCounts = true;
    public static int EtherealPlayedCount = 0;
    
    static int freeYourMindFloor = -1;
    
    //region color definitions
    public static final Color corruptedGlow = new Color(1f, 0, 1f, 1);
    public static final Color etherealTextColor = new Color(1f, .7f, 1f, 1);
    public static final Color normalGlowColor = new Color(0.2F, 0.9F, 1.0F, 0.25F);
    public static final Color retainGlowColor = new Color(1f, 0.7F, .5F, .5F);
    public static final Color dimTextColor = new Color(0.3f, 0.3F, .3F, 1);
    public static final Color dimCardColor = new Color(0.3f, 0.3F, .3F, .1f);
    //endregion
    
    public static HashMap<String, Texture> imgMap = new HashMap<>();
    public static final String CantRetrieveFootnote = "This can't be Retrieved.";
    public static final String ExhaustTargetFootnote = "Does nothing if it NL lacks a target card.";
    
    static TextureAtlas.AtlasRegion darkFireAtkImg;
    static boolean hasAddedKeywords = false;
    
    //region queries
    public static int getInitialCardRemovalCost()
    {
        if (AbstractDungeon.ascensionLevel >= 16)
        {
            return BcUtility.InitialRemovalCostA16;
        }
        else
        {
            return BcUtility.InitialRemovalCost;
        }
    }
    
    public static int getCardRemovalCostIncrement()
    {
        if (AbstractDungeon.ascensionLevel >= 16)
        {
            return BcUtility.RemovalCostIncrementA16;
        }
        else
        {
            return BcUtility.RemovalCostIncrement;
        }
    }
    
    public static TextureAtlas.AtlasRegion getDarkFireAttackImg()
    {
        if (darkFireAtkImg == null)
        {
            Texture texture = getTexture("bcBalanceModResources/images/vfx/bluePurpleFire300x300.png");
            darkFireAtkImg = new TextureAtlas.AtlasRegion(
                    texture,
                    0,
                    0,
                    texture.getWidth(),
                    texture.getHeight());
        }
        
        return darkFireAtkImg;
    }
    
    public static Texture getTexture(String path)
    {
        Texture texture;
        if (imgMap.containsKey(path))
        {
            texture = imgMap.get(path);
        }
        else
        {
            texture = ImageMaster.loadImage(path);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            imgMap.put(path, texture);
        }
        
        return texture;
    }
    
    public static int getCurseRemovalSacrificeAmount()
    {
        return RitualCurseRemovalSacrifice;
    }
    
    public static boolean isCombatCard(AbstractCard card)
    {
        if (isPlayerInCombat())
        {
            AbstractPlayer player = AbstractDungeon.player;
            if (player.hand.contains(card) ||
                        player.drawPile.contains(card) ||
                        player.discardPile.contains(card) ||
                        player.exhaustPile.contains(card))
            {
                return true;
            }
        }
        
        return false;
    }

    public static boolean canBeRetrieved(AbstractCard card)
    {
        if (card instanceof BcCardBase)
        {
            return ((BcCardBase)card).canBeRetrieved();
        }
        
        return true;
    }

    public static int getCurrentFocus()
    {
        if (isPlayerInCombat())
        {
            AbstractPower focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID);
            if (focus != null)
            {
                return focus.amount;
            }
        }
        
        return 0;
    }
    
    public static boolean isZeroCostCard(AbstractCard card)
    {
        return (card.costForTurn == 0) ||
            card.freeToPlayOnce ||
            ((card.cost == -1) && (EnergyPanel.totalCount == 0)); //x-cost with zero energy remaining
    }
    
    public static void markFreeYourMind()
    {
        freeYourMindFloor = AbstractDungeon.floorNum;
    }
    
    public static void resetFreeYourMind()
    {
        freeYourMindFloor = -1;
    }
    
    public static boolean shouldFreeYourMind()
    {
        if ((freeYourMindFloor != -1) &&
            (freeYourMindFloor != AbstractDungeon.floorNum))
        {
            //its a one time thing
            return false;
        }
        
        boolean hasFear = false;
        boolean hasDoubt = false;
        boolean hasDisbelief = false;
        
        if ((AbstractDungeon.player != null) &&
            (AbstractDungeon.player.masterDeck != null))
        {
            for (int i = 0; i < AbstractDungeon.player.masterDeck.group.size(); i++)
            {
                AbstractCard card = AbstractDungeon.player.masterDeck.group.get(i);
                if (card.cardID == Fear.ID)
                {
                    hasFear = true;
                }
                else if (card.cardID == Doubt.ID)
                {
                    hasDoubt = true;
                }
                else if (card.cardID == Disbelief.ID)
                {
                    hasDisbelief = true;
                }
            }
        }
        
        return (hasFear && hasDoubt && hasDisbelief);
    }

    public static int getBlockToLose(int currentBlock)
    {
        if (AbstractDungeon.player.hasPower(BarricadePower.POWER_ID) ||
            AbstractDungeon.player.hasPower(Blur.ID) ||
            (BcUtility.isPlayerInStance(WrathStance.STANCE_ID) && AbstractDungeon.player.hasPower(NaturalViolentFirePower.POWER_ID)))
        {
            return 0;
        }
        
        if (AbstractDungeon.player.hasRelic(Calipers.ID))
        {
            return Math.min(currentBlock, Calipers.BLOCK_LOSS);
        }
        
        return currentBlock;
    }

    public static int getEmptyOrbSlotCount()
    {
        int emptySlotCount = 0;
        for(AbstractOrb orb : AbstractDungeon.player.orbs)
        {
            if (orb instanceof EmptyOrbSlot)
            {
                emptySlotCount++;
            }
        }

        return emptySlotCount;
    }
    
    public static int getFilledOrbSlotCount()
    {
        if (!isPlayerInCombat())
        {
            return 0;
        }
        else
        {
            return AbstractDungeon.player.orbs.size() - getEmptyOrbSlotCount();
        }
    }
    
    public static boolean isPlayerInCombat()
    {
        return (AbstractDungeon.player != null) &&
                       (AbstractDungeon.currMapNode != null) &&
                       (AbstractDungeon.getCurrRoom() != null) &&
                       (AbstractDungeon.getMonsters() != null) &&
                       !AbstractDungeon.getMonsters().areMonstersBasicallyDead();
    }
    
    public static int getCurrentStrength()
    {
        if (isPlayerInCombat())
        {
            AbstractPower strength = AbstractDungeon.player.getPower(StrengthPower.POWER_ID);
            if (strength != null)
            {
                return strength.amount;
            }
        }
        
        return 0;
    }
    
    public static boolean playerHasPower(String powerId)
    {
        if (isPlayerInCombat())
        {
            AbstractPower power = AbstractDungeon.player.getPower(powerId);
            return power != null;
        }
        
        return false;
    }
    
    public static int getPowerAmount(String powerId, AbstractCreature owner)
    {
        if (isPlayerInCombat())
        {
            AbstractPower power = owner.getPower(powerId);
            if (power != null)
            {
                return power.amount;
            }
        }
        
        return 0;
    }
    
    public static AbstractPower getPower(String powerId)
    {
        if (isPlayerInCombat())
        {
            return AbstractDungeon.player.getPower(powerId);
        }
        
        return null;
    }
    
    public static int getPowerAmount(String powerId)
    {
        if (isPlayerInCombat())
        {
            AbstractPower power = AbstractDungeon.player.getPower(powerId);
            if (power != null)
            {
                return power.amount;
            }
        }
        
        return 0;
    }
    
    //takes both hand space and all pending draw actions into account
    public static int getAvailableHandSpace()
    {
        if (isPlayerInCombat())
        {
            AbstractPlayer player = AbstractDungeon.player;
            int handSpace = 10 - player.hand.size();
            
            for (AbstractGameAction action : AbstractDungeon.actionManager.actions)
            {
                if (action instanceof DrawCardAction)
                {
                    DrawCardAction drawCardAction = (DrawCardAction) action;
                    handSpace -= drawCardAction.amount;
                }
            }
            
            handSpace = Math.max(0, handSpace);
            
            return handSpace;
        }
        else
        {
            return 0;
        }
    }
    
    public static boolean isPlayerInStance(String stanceId)
    {
        if (isPlayerInCombat())
        {
            String currentStance = (AbstractDungeon.player.stance != null) ? AbstractDungeon.player.stance.ID : null;
            
            if (stanceId == NeutralStance.STANCE_ID)
            {
                return
                    (currentStance != WrathStance.STANCE_ID) &&
                    (currentStance != CalmStance.STANCE_ID) &&
                    (currentStance != DivinityStance.STANCE_ID);
            }
            else
            {
                return
                    (currentStance != null) &&
                    (currentStance == stanceId);
            }
        }
        
        return false;
    }
    
    public static AbstractMonster getRandomTarget()
    {
        return AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
    }
    
    static Color grayColor = new Color(.5f, .5f, .5f, 1);
    
    public static Color getStringColor(char colorChar)
    {
        if (colorChar == 'b')
        {
            return Settings.BLUE_TEXT_COLOR;
        }
        else if (colorChar == 'r')
        {
            return Settings.RED_TEXT_COLOR;
        }
        else if (colorChar == 'g')
        {
            return Settings.GREEN_TEXT_COLOR;
        }
        else if (colorChar == 'a')
        {
            return grayColor;
        }
        else
        {
            return Settings.CREAM_COLOR;
        }
    }
    
    public static String getModifiedBlockString(int originalBlock)
    {
        return getModifiedValueString(originalBlock, getModifiedBlock(originalBlock));
    }
    
    public static String getModifiedValueString(int originalValue, int newValue)
    {
        if (newValue == originalValue)
        {
            return Integer.toString(newValue);
        }
        else if (newValue > originalValue)
        {
            return "#g" + newValue;
        }
        else //if (newValue < originalValue)
        {
            return "#r" + newValue;
        }
    }
    
    public static int getModifiedBlock(int originalBlock)
    {
        if (!isPlayerInCombat())
        {
            return originalBlock;
        }
        
        AbstractPlayer player = AbstractDungeon.player;
        
        float block = (float) originalBlock;
        
        for (AbstractPower power : player.powers)
        {
            block = power.modifyBlock(block);
        }
        
        for (AbstractPower power : player.powers)
        {
            block = power.modifyBlockLast(block);
        }
        
        if (block < 0.0F)
        {
            block = 0.0F;
        }
        
        return MathUtils.floor(block);
    }

    public static String getCardCountString(int cardCount)
    {
        if (cardCount == 1)
        {
            return "a card";
        }
        else
        {
            return cardCount + " cards";
        }
    }

    public static String getEnergyString(int energy, AbstractCard card)
    {
        //silent
        String oneEnergy = "";
        
        if (card.color == AbstractCard.CardColor.GREEN)
        {
            oneEnergy = "[G]";
        }
        else if (card.color == AbstractCard.CardColor.RED)
        {
            oneEnergy = "[R]";
        }
        else if (card.color == AbstractCard.CardColor.BLUE)
        {
            oneEnergy = "[B]";
        }
        else if ((card.color == AbstractCard.CardColor.PURPLE) ||
            (card.color == AbstractCard.CardColor.COLORLESS))
        {
            oneEnergy = "[W]";
        }
        
        if (energy <= 0)
        {
            return "(none)";
        }
        else if (energy == 1)
        {
            return oneEnergy;
        }
        else
        {
            StringBuilder blah = new StringBuilder(oneEnergy);
            for (int i = 0; i < (energy - 1); i++)
            {
                blah.insert(0, oneEnergy + " ");
            }
            
            return blah.toString();
        }
    }

    public static String getScryString(int baseScry)
    {
        int actualScry = baseScry + BcUtility.getPowerAmount(ForesightPower.POWER_ID);
        
        if ((AbstractDungeon.player != null) && AbstractDungeon.player.hasRelic("GoldenEye"))
        {
            actualScry += 2;
        }
        
        if (actualScry > baseScry)
        {
            return "#g" + actualScry;
        }
        else
        {
            return Integer.toString(actualScry);
        }
    }
    
    public static String getCharacterName()
    {
        switch (AbstractDungeon.player.chosenClass)
        {
            case IRONCLAD:
                return "Ironclad";
            case THE_SILENT:
                return "Silent";
            case DEFECT:
                return "Defect";
            case WATCHER:
                return "Watcher";
            default:
                return AbstractDungeon.player.name;
        }
    }

    public static int getOrbNumber(AbstractOrb orb)
    {
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
        {
            if (AbstractDungeon.player.orbs.get(i) == orb)
            {
                return i;
            }
        }
        
        return -1;
    }
    
    public static int getActualCardCost(AbstractCard card)
    {
        if (!BcUtility.isPlayerInCombat())
        {
            return card.cost;
        }
        else
        {
            if (card.cost == -1) //x-cost
            {
                return EnergyPanel.totalCount;
            }
            else if (card.freeToPlay()) //costForTurn still returns non-zero when its actually free.
            {
                return 0;
            }
            else
            {
                return card.costForTurn;
            }
        }
    }

    public static int countOrbsOfType(String orbTypeId)
    {
        int count = 0;
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
        {
            if (AbstractDungeon.player.orbs.get(i).ID == orbTypeId)
            {
                count++;
            }
        }

        return count;
    }
    //endregion

    //region Animations
    public static void EndTurnEarly()
    {
        if (isPlayerInCombat())
        {
            AbstractDungeon.actionManager.addToBottom(new PressEndTurnButtonAction());

            for (int i = 0; i < 120; i++)
            {
                AbstractDungeon.effectsQueue.add(new EndTurnEarlyEffect(i * .02f));
            }
        }
    }
    //endregion

    //region get cards
    public static CardGroup getAllPossibleCards(AbstractCard.CardRarity rarity,
                                                AbstractCard.CardType type,
                                                AbstractCard.CardColor nativeColor,
                                                boolean includeColorless,
                                                boolean includeNative,
                                                boolean includeForeign,
                                                boolean allowHealing,
                                                String excludedId)
    {
        try
        {
            if (type == AbstractCard.CardType.STATUS)
            {
                includeColorless = true;
            }
            
            boolean includeStatuses = type == AbstractCard.CardType.STATUS;
            boolean includeCurses = (type == AbstractCard.CardType.CURSE) || (rarity == AbstractCard.CardRarity.CURSE);
            
            if (nativeColor == null)
            {
                nativeColor = AbstractDungeon.player.getCardColor();
            }
            
            if (nativeColor == AbstractCard.CardColor.COLORLESS)
            {
                includeColorless = true;
            }
            
            CardGroup possibleCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard card : CardLibrary.getAllCards())
            {
                if (card.cardID.equals(excludedId))
                {
                    continue;
                }
                
                //exclude basic and special by default unless explicitly requested.
                if (((card.rarity == AbstractCard.CardRarity.BASIC) || (card.rarity == AbstractCard.CardRarity.SPECIAL)) &&
                            rarity == null)
                {
                    continue;
                }
                
                if ((rarity != null) &&
                            (card.rarity != rarity))
                {
                    continue;
                }
                
                if ((type != null) &&
                            (card.type != type))
                {
                    continue;
                }
                
                if (!includeStatuses &&
                            (card.type == AbstractCard.CardType.STATUS))
                {
                    continue;
                }
                
                if (!includeCurses &&
                            (card.type == AbstractCard.CardType.CURSE))
                {
                    continue;
                }
                
                if (!includeColorless &&
                            (card.color == AbstractCard.CardColor.COLORLESS))
                {
                    continue;
                }
                
                if (!includeNative &&
                            (card.color == nativeColor))
                {
                    continue;
                }
                
                if (!includeForeign &&
                            (card.color != nativeColor) &&
                            (card.color != AbstractCard.CardColor.COLORLESS) &&
                            (card.color != AbstractCard.CardColor.CURSE))
                {
                    continue;
                }
                
                if (!allowHealing &&
                            card.hasTag(AbstractCard.CardTags.HEALING))
                {
                    continue;
                }
                
                possibleCards.addToBottom(card.makeStatEquivalentCopy());
            }
            return possibleCards;
        }
        catch (Exception ex)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            BcBalanceMod.logger.info("error in getCards");
            BcBalanceMod.logger.info(sStackTrace);
        }
        
        return null;
    }
    
    public static ArrayList<AbstractCard> getRandomCards(AbstractCard.CardRarity rarity,
                                                         AbstractCard.CardType type,
                                                         boolean includeColorless,
                                                         boolean includeNative,
                                                         boolean includeForeign,
                                                         int amount,
                                                         boolean upgraded,
                                                         boolean allowHealing,
                                                         String excludedId)
    {
        CardGroup possibleCards = getAllPossibleCards(rarity, type, null, includeColorless, includeNative, includeForeign, allowHealing, excludedId);

        if (upgraded)
        {
            for(AbstractCard card : possibleCards.group)
            {
                card.upgrade();
            }
        }

        return getRandomCardsFromGroup(possibleCards, amount);
    }
    
    public static AbstractCard getRandomCard(AbstractCard.CardRarity rarity,
                                             AbstractCard.CardType type,
                                             boolean includeColorless,
                                             boolean includeNative,
                                             boolean includeForeign,
                                             boolean allowHealing)
    
    {
        CardGroup possibleCards = getAllPossibleCards(
            rarity,
            type,
            null,
            includeColorless,
            includeNative,
            includeForeign,
            allowHealing,
            null);
            
        if (possibleCards.isEmpty())
        {
            return null;
        }
        else
        {
            return possibleCards.getRandomCard(AbstractDungeon.cardRandomRng);
        }
    }
    
    static ArrayList<AbstractCard> getRandomCardsFromGroup(CardGroup group, int amount)
    {
        group.shuffle();
        
        amount = Math.min(amount, group.size());
        
        ArrayList<AbstractCard> result = new ArrayList<>();
        for (int i = 0; i < amount; i++)
        {
            result.add(group.group.get(i));
        }
        
        return result;
    }
    
    public static void removeCardFromMasterLibrary(String cardId)
    {
        AbstractCard card = CardLibrary.getCard(cardId);
        if (card == null)
        {
            return;
        }
        
        switch (card.color)
        {
            case RED:
                CardLibrary.redCards--;
                if (UnlockTracker.isCardSeen(card.cardID))
                {
                    CardLibrary.seenRedCards--;
                }
                break;
            case GREEN:
                CardLibrary.greenCards--;
                if (UnlockTracker.isCardSeen(card.cardID))
                {
                    CardLibrary.seenGreenCards--;
                }
                break;
            case PURPLE:
                CardLibrary.purpleCards--;
                if (UnlockTracker.isCardSeen(card.cardID))
                {
                    CardLibrary.seenPurpleCards--;
                }
                break;
            case BLUE:
                CardLibrary.blueCards--;
                if (UnlockTracker.isCardSeen(card.cardID))
                {
                    CardLibrary.seenBlueCards--;
                }
                break;
            case COLORLESS:
                CardLibrary.colorlessCards--;
                if (UnlockTracker.isCardSeen(card.cardID))
                {
                    CardLibrary.seenColorlessCards--;
                }
                break;
            case CURSE:
                CardLibrary.curseCards--;
                if (UnlockTracker.isCardSeen(card.cardID))
                {
                    CardLibrary.seenCurseCards--;
                }
                //CardLibrary.curses.put(card.cardID, card);
        }
        
        if (!UnlockTracker.isCardSeen(card.cardID))
        {
            card.isSeen = false;
        }
        
        CardLibrary.cards.remove(card.cardID);
        CardLibrary.totalCardCount--;
    }
    
    public static void log(String msg)
    {
        BcBalanceMod.logger.info(msg);
    }
    
    public static void logAllCards()
    {
        if (LogCardCounts)
        {
            logCardsForCharacter("Ironclad", AbstractCard.CardColor.RED);
            logCardsForCharacter("Silent", AbstractCard.CardColor.GREEN);
            logCardsForCharacter("Defect", AbstractCard.CardColor.BLUE);
            logCardsForCharacter("Watcher", AbstractCard.CardColor.PURPLE);
        }
    }
    
    static CardGroup getCardGroup(AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardType type)
    {
        CardGroup cards = getAllPossibleCards(rarity, type, color, false, true, false,true, null);
        cards.sortAlphabetically(true);
        
        return cards;
    }
    
    static void logCardsForCharacter(String characterName, AbstractCard.CardColor color)
    {
        CardGroup commonAttacks = getCardGroup(color, AbstractCard.CardRarity.COMMON, AbstractCard.CardType.ATTACK);
        CardGroup commonSkills = getCardGroup(color, AbstractCard.CardRarity.COMMON, AbstractCard.CardType.SKILL);
        CardGroup uncommonAttacks = getCardGroup(color, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardType.ATTACK);
        CardGroup uncommonSkills = getCardGroup(color, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardType.SKILL);
        CardGroup uncommonPowers = getCardGroup(color, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardType.POWER);
        CardGroup rareAttacks = getCardGroup(color, AbstractCard.CardRarity.RARE, AbstractCard.CardType.ATTACK);
        CardGroup rareSkills = getCardGroup(color, AbstractCard.CardRarity.RARE, AbstractCard.CardType.SKILL);
        CardGroup rarePowers = getCardGroup(color, AbstractCard.CardRarity.RARE, AbstractCard.CardType.POWER);
        
        String indent = "";
        Logger log = BcBalanceMod.logger;
        
        int total = commonAttacks.size() + uncommonAttacks.size() + rareAttacks.size() +
                            commonSkills.size() + uncommonSkills.size() + rareSkills.size()
                            + uncommonPowers.size() + rarePowers.size();
        
        log.info(characterName + " Cards(" + total + ")");
        log.info("  [ " + (rareAttacks.size() + rareSkills.size() + rarePowers.size()) + " Rares ]");
        log.info("  [ " + (uncommonAttacks.size() + uncommonSkills.size() + uncommonPowers.size()) + " Uncommons ]");
        log.info("  [ " + (commonAttacks.size() + commonSkills.size()) + " Commons ]");
        indent = "      -";
        log.info("  -Attacks(" + (commonAttacks.size() + uncommonAttacks.size() + rareAttacks.size()) + ")");
        log.info("    -Common(" + commonAttacks.size() + ")");
        logCardGroup(indent, commonAttacks);
        log.info("    -Uncommon(" + uncommonAttacks.size() + ")");
        logCardGroup(indent, uncommonAttacks);
        log.info("    -Rare(" + rareAttacks.size() + ")");
        logCardGroup(indent, rareAttacks);
        
        log.info("  -Skills(" + (commonSkills.size() + uncommonSkills.size() + rareSkills.size()) + ")");
        log.info("    -Common(" + commonSkills.size() + ")");
        logCardGroup(indent, commonSkills);
        log.info("    -Uncommon(" + uncommonSkills.size() + ")");
        logCardGroup(indent, uncommonSkills);
        log.info("    -Rare(" + rareSkills.size() + ")");
        logCardGroup(indent, rareSkills);
        
        log.info("  -Powers(" + (uncommonPowers.size() + rarePowers.size()) + ")");
        log.info("    -Uncommon(" + uncommonPowers.size() + ")");
        logCardGroup(indent, uncommonPowers);
        log.info("    -Rare(" + rarePowers.size() + ")");
        logCardGroup(indent, rarePowers);
    }
    
    static void logCardGroup(String indent, CardGroup cardGroup)
    {
//        for (AbstractCard card : cardGroup.group)
//        {
//            BcBalanceMod.logger.info(indent + card.name);
//        }
    }
    //endregion
    
    //region card manipulation
    public static void setGlowColor(AbstractCard card, Color color)
    {
        card.glowColor = color.cpy();
        if (card instanceof BcCardBase)
        {
            BcCardBase bcCard = (BcCardBase) card;
            bcCard.defaultGlow = color.cpy();
        }
    }
    
    public static void makeCardEthereal(AbstractCard card)
    {
        card.isEthereal = true;
        if (card instanceof BcCardBase)
        {
            ((BcCardBase) card).isManuallyEthereal = true;
        }
    }
    
    public static void makeCardTemporary(AbstractCard card)
    {
        card.exhaustOnUseOnce = true;
        card.isEthereal = true;
        if (card instanceof BcCardBase)
        {
            ((BcCardBase) card).isManuallyEthereal = true;
        }
    }
    
    public static boolean isCardTemporary(AbstractCard card)
    {
        return card.exhaustOnUseOnce && card.isEthereal;
    }
    
    public static void addNewCardToHandOrDiscard(AbstractCard card)
    {
        card.current_x = -1000.0F * Settings.xScale;
        if (AbstractDungeon.player.hand.size() < 10)
        {
            AbstractDungeon.effectList.add(
                    new ShowCardAndAddToHandEffect(
                            card,
                            (float) Settings.WIDTH / 2.0F,
                            (float) Settings.HEIGHT / 2.0F));
        }
        else
        {
            if (!isCardTemporary(card))
            {
                AbstractDungeon.effectList.add(
                        new ShowCardAndAddToDiscardEffect(
                                card,
                                (float) Settings.WIDTH / 2.0F,
                                (float) Settings.HEIGHT / 2.0F));
            }
        }
    }
    //endregion
    
    //region monsters
    public static boolean lastTwoMoves(AbstractMonster monster, byte move)
    {
        ArrayList<Byte> moveHistory = monster.moveHistory;
        
        if (moveHistory.isEmpty())
        {
            return false;
        }
        
        return ((moveHistory.get(moveHistory.size() - 1) == move) ||
                        ((moveHistory.size() > 1) && (moveHistory.get(moveHistory.size() - 2) == move)));
    }
    //endregion
    
    //region relics
    public static ArrayList<String> getBossRelicsToTreatAsRare()
    {
        ArrayList<String> bossToRareList = new ArrayList<>();
        bossToRareList.add(CallingBell.ID);
        bossToRareList.add(BlackStar.ID);
        bossToRareList.add(TinyHouse.ID);
        
        return bossToRareList;
    }
    
    public static ArrayList<String> getCharacterRelics()
    {
        ArrayList<String> characterRelics = new ArrayList<>();
        
        switch (AbstractDungeon.player.chosenClass)
        {
            case IRONCLAD:
                //common
                characterRelics.add(RedSkull.ID);
                //uncommon
                characterRelics.add(PaperFrog.ID);
                characterRelics.add(SelfFormingClay.ID);
                //rare
                characterRelics.add(ChampionsBelt.ID);
                characterRelics.add(CharonsAshes.ID);
                characterRelics.add(MagicFlower.ID);
                characterRelics.add(RunicCube.ID);
                //shop
                characterRelics.add(Brimstone.ID);
                break;
            case THE_SILENT:
                //common
                characterRelics.add(SneckoSkull.ID);
                //uncommon
                characterRelics.add(NinjaScroll.ID);
                characterRelics.add(PaperCrane.ID);
                //rare
                characterRelics.add(TheSpecimen.ID);
                characterRelics.add(Tingsha.ID);
                characterRelics.add(ToughBandages.ID);
                characterRelics.add(WristBlade.ID);
                //shop
                characterRelics.add(TwistedFunnel.ID);
                break;
            case DEFECT:
                //common
                characterRelics.add(DataDisk.ID);
                //uncommon
                characterRelics.add(GoldPlatedCables.ID);
                characterRelics.add(SymbioticVirus.ID);
                //rare
                characterRelics.add(EmotionChip.ID);
                characterRelics.add(PersonalDigitalAssistant.ID);
                characterRelics.add(RotaryMechanism.ID);
                characterRelics.add(Inserter.ID);
                //shop
                characterRelics.add(RunicCapacitor.ID);
                break;
            case WATCHER:
                //common
                characterRelics.add(Damaru.ID);
                //uncommon
                characterRelics.add(Duality.ID);
                characterRelics.add(TeardropLocket.ID);
                //rare
                characterRelics.add(CloakClasp.ID);
                characterRelics.add(GoldenEye.ID);
                characterRelics.add(GoldenFeather.ID);
                //shop
                characterRelics.add(BcMelange.ID);
                break;
        }
        
        return characterRelics;
    }
    
    public static AbstractRelic getPlayerRelic(String relicId)
    {
        for (AbstractRelic relic : AbstractDungeon.player.relics)
        {
            if (relic.relicId.equals(relicId))
            {
                return relic;
            }
        }
        
        return null;
    }
    
    public static boolean playerHasRelic(String relicId)
    {
        if ((AbstractDungeon.player != null) &&
                    (AbstractDungeon.currMapNode != null) &&
                    (AbstractDungeon.getCurrRoom() != null))
        {
            for (AbstractRelic relic : AbstractDungeon.player.relics)
            {
                if (relic.relicId.equals(relicId))
                {
                    return true;
                }
            }
        }
        
        
        return false;
    }
    
    public static ArrayList<AbstractRelic> getRandomRelics(int amount, AbstractRelic.RelicTier tier, ArrayList<String> additionalChoices, ArrayList<String> relicsToExclude)
    {
        ArrayList<AbstractRelic> randomRelics = new ArrayList<AbstractRelic>();
        
        while (randomRelics.size() < amount)
        {
            AbstractRelic potentialRelic = getRandomRelic(tier, additionalChoices, relicsToExclude);
            
            boolean isDuplicate = false;
            for (AbstractRelic existingRelic : randomRelics)
            {
                if (existingRelic.relicId.equals(potentialRelic.relicId))
                {
                    isDuplicate = true;
                }
            }
            
            if (!isDuplicate)
            {
                randomRelics.add(potentialRelic);
            }
        }
        
        return randomRelics;
    }
    
    public static AbstractRelic getRandomBossRelic(boolean excludeEvolvedStarterRelics)
    {
        String relicId = null;
        while (relicId == null)
        {
            relicId = returnRandomRelicId(AbstractRelic.RelicTier.BOSS, null, null);
            
            if (excludeEvolvedStarterRelics)
            {
                if (relicId.equals(BlackBlood.ID) ||
                            relicId.equals(RingOfTheSerpent.ID) ||
                            relicId.equals(FrozenCore.ID) ||
                            relicId.equals(HolyWater.ID))
                {
                    relicId = null;
                }
            }
        }
        
        return RelicLibrary.getRelic(relicId).makeCopy();
    }
    
    public static AbstractRelic getRandomRelic(AbstractRelic.RelicTier tier, ArrayList<String> additionalChoices, ArrayList<String> relicsToExclude)
    {
        return RelicLibrary.getRelic(returnRandomRelicId(tier, additionalChoices, relicsToExclude)).makeCopy();
    }

    public static AbstractRelic getRandomRelic(ArrayList<String> choices)
    {
        return RelicLibrary.getRelic(getRandomRelicIdFromPool(null, choices, null)).makeCopy();
    }
    
    public static void removeRelicFromDungeon(String relicKey)
    {
        AbstractDungeon.commonRelicPool.remove(relicKey);
        AbstractDungeon.uncommonRelicPool.remove(relicKey);
        AbstractDungeon.rareRelicPool.remove(relicKey);
        AbstractDungeon.shopRelicPool.remove(relicKey);
        AbstractDungeon.bossRelicPool.remove(relicKey);
    }

    static String returnRandomRelicId(AbstractRelic.RelicTier tier, ArrayList<String> additionalChoices, ArrayList<String> relicsToExclude)
    {
        String retVal = null;
        while (retVal == null)
        {
            if (tier == null)
            {
                retVal = getRandomRelicIdFromPool(null, additionalChoices, relicsToExclude);
            }
            else
            {
                switch (tier)
                {
                    case COMMON:
                        if (AbstractDungeon.commonRelicPool.isEmpty())
                        {
                            retVal = returnRandomRelicId(AbstractRelic.RelicTier.UNCOMMON, additionalChoices, relicsToExclude);
                        }
                        else
                        {
                            retVal = getRandomRelicIdFromPool(AbstractDungeon.commonRelicPool, additionalChoices, relicsToExclude);
                        }
                        break;
                    case UNCOMMON:
                        if (AbstractDungeon.uncommonRelicPool.isEmpty())
                        {
                            retVal = returnRandomRelicId(AbstractRelic.RelicTier.RARE, additionalChoices, relicsToExclude);
                        }
                        else
                        {
                            retVal = getRandomRelicIdFromPool(AbstractDungeon.uncommonRelicPool, additionalChoices, relicsToExclude);
                        }
                        break;
                    case RARE:
                        if (AbstractDungeon.rareRelicPool.isEmpty())
                        {
                            retVal = "Circlet";
                        }
                        else
                        {
                            retVal = getRandomRelicIdFromPool(AbstractDungeon.rareRelicPool, additionalChoices, relicsToExclude);
                        }
                        break;
                    case SHOP:
                        if (AbstractDungeon.shopRelicPool.isEmpty())
                        {
                            retVal = returnRandomRelicId(AbstractRelic.RelicTier.UNCOMMON, additionalChoices, relicsToExclude);
                        }
                        else
                        {
                            retVal = getRandomRelicIdFromPool(AbstractDungeon.shopRelicPool, additionalChoices, relicsToExclude);
                        }
                        break;
                    case BOSS:
                        if (AbstractDungeon.bossRelicPool.isEmpty())
                        {
                            retVal = "Red Circlet";
                        }
                        else
                        {
                            retVal = getRandomRelicIdFromPool(AbstractDungeon.bossRelicPool, additionalChoices, relicsToExclude);
                        }
                        break;
                    default:
                }
            }
            
            if (!RelicLibrary.getRelic(retVal).canSpawn())
            {
                retVal = null;
            }
        }
        
        return retVal;
    }
    
    static String getRandomRelicIdFromPool(ArrayList<String> relicPool, ArrayList<String> additionalChoices, ArrayList<String> relicsToExclude)
    {
        ArrayList<String> finalRelicPool = new ArrayList<>();
        if (relicPool != null)
        {
            finalRelicPool.addAll(relicPool);
        }

        if (additionalChoices != null)
        {
            for (String additionalChoice : additionalChoices)
            {
                boolean isDuplicate = false;
                for (String relic : finalRelicPool)
                {
                    if (additionalChoice.equals(relic))
                    {
                        isDuplicate = true;
                        break;
                    }
                }

                if (!isDuplicate)
                {
                    finalRelicPool.add(additionalChoice);
                }
            }
        }
        
        if (relicsToExclude != null)
        {
            finalRelicPool.removeAll(relicsToExclude);
        }

        return finalRelicPool.get(AbstractDungeon.relicRng.random(0, finalRelicPool.size() - 1));
    }
    //endregion
    
    //region string, int, float utility
    public static boolean isNullEmptyOrWhiteSpace(String string)
    {
        return (string == null) || (string.trim().isEmpty());
    }
    
    public static int clamp(int min, int value, int max)
    {
        return Math.max(min, Math.min(max, value));
    }
    
    public static boolean isInteger(String str)
    {
        try
        {
            Integer.parseInt(str.trim());
            return true;
        }
        catch (NumberFormatException nfe)
        {
        }
        return false;
    }

    public static String formatFloat(float value)
    {
        int i = (int) value;
        return value == i ? String.valueOf(i) : String.valueOf(value);
    }
    //endregion
    
    //region system fixes & new keywords
    public static void populateLocalizationPlaceholders()
    {
        String modName = BcBalanceMod.getModID();
        Map localizationStrings = ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "cards");
        localizationStrings.put(modName + ":temp", new CardStrings());
        
        for (AbstractCard card : CardLibrary.getAllCards())
        {
            if (card.cardID.startsWith(modName))
            {
                localizationStrings.put(card.cardID, new CardStrings());
            }
        }
        
        //ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, typeMap, localizationStrings);
    }
    
    public static void addKeywords()
    {
        if (!hasAddedKeywords)
        {
            hasAddedKeywords = true;
            addKeyword(
                    new String[]{"rebound", "rebounded", "rebounds"},
                    "Rebounded cards are added to the top of the Draw Pile.");
            
            addKeyword(
                    new String[]{"exhume", "exhumes", "exhumed"},
                    "Exhuming a card brings it back from the Exhaust Pile and adds it to your hand.");
            
            addKeyword(
                    new String[]{"retrieve", "retrieves", "retrieved", "retrieved.", "retrieved.)"},
                    "Retrieving a card brings it back from the discard pile and puts it in your hand.");
                    
            addKeyword(
                    new String[]{"temporary", "temporarily"},
                    "Temporary Cards exhaust when played, discarded or at the end of your turn. Temporary Strength and Dexterity are lost at the end of the turn.");
            
            GameDictionary.keywords.put("retain", "Retained cards are not discarded at the end of turn. NL Retained #yBlock doesn't expire at the start of your next turn.");
        }
    }
    
    static void addKeyword(String[] names, String description)
    {
        for (String name : names)
        {
            GameDictionary.keywords.put(name, description);
            GameDictionary.parentWord.put(name, names[0]);
        }
    }
    //endregion
}
