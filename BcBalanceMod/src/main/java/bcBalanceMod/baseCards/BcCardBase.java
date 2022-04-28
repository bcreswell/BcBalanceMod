package bcBalanceMod.baseCards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import bcBalanceMod.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.*;

import static bcBalanceMod.BcBalanceMod.makeCardPath;

public abstract class BcCardBase extends CustomCard
{
    static String noneImgPath = makeCardPath("none.png");
    protected CardStrings cardStrings;
    public Color defaultGlow = null;
    static final String tempId = BcBalanceMod.makeID("temp");
    
    public BcCardBase()
    {
        //java constructors are just as dumb as c# ones.
        super(tempId, tempId, noneImgPath, -2, tempId, CardType.STATUS, CardColor.COLORLESS, CardRarity.BASIC, CardTarget.SELF);
    
        cardID = getId();
        //pull in the card strings the old "correct" way so that i dont have to override
        // getDisplayName() and getDescription() when changing base game cards unless i want to.
        cardStrings = CardCrawlGame.languagePack.getCardStrings(cardID);
        
        //todo: figure out how to make the displayName always fit.
        //could use Settings.BIG_TEXT_MODE and add spaces
        //but should probably try to figure out the font size thing in base class
        originalName = getDisplayName();
        
        name = originalName;
        cost = getCost();
        costForTurn = cost;
        type = getCardType();
        color = getCardColor();
        rarity = getCardRarity();
        target = getCardTarget();
        
        damageType = getDamageType();
        damageTypeForTurn = damageType;
        
        baseDamage = getDamage();
        damage = baseDamage;
        
        baseBlock = getBlock();
        block = baseBlock;
        
        baseMagicNumber = getMagicNumber();
        magicNumber = baseMagicNumber;
        
        exhaust = getExhaust();
        selfRetain = getRetain();
        isEthereal = getEthereal();
        isInnate = getInnate();
        
        isMultiDamage = isAoeAttack();
        
        //base game paths refer to a name in an atlas while custom paths refer to a path to a .png file
        if (!getImagePath().endsWith(".png"))
        {
            assetUrl = getImagePath();
            TextureAtlas cardAtlas = ReflectionHacks.getPrivateStatic(AbstractCard.class, "cardAtlas");
            TextureAtlas oldCardAtlas = ReflectionHacks.getPrivateStatic(AbstractCard.class, "oldCardAtlas");
            portrait = cardAtlas.findRegion(assetUrl);
            jokePortrait = oldCardAtlas.findRegion(assetUrl);
            
            if (portrait == null)
            {
                if (jokePortrait != null)
                {
                    portrait = jokePortrait;
                }
                else
                {
                    portrait = cardAtlas.findRegion("status/beta");
                }
            }
        }
        else
        {
            textureImg = makeCardPath(getImagePath());
            loadCardImage(textureImg);
        }
        
        onInitialized();
        
        rawDescription = getFullDescription();
        initializeTitle();
        initializeDescription();
        
        ReflectionHacks.RMethod createCardImage = ReflectionHacks.privateMethod(AbstractCard.class, "createCardImage");
        createCardImage.invoke(this);
    }
    
    @Override
    protected void initializeTitle()
    {
        super.initializeTitle();

        boolean useSmallFont = originalName.length() >= 18;
        boolean existingValue = ReflectionHacks.getPrivate(this, AbstractCard.class, "useSmallTitleFont");
        
        if (useSmallFont && !existingValue)
        {
            ReflectionHacks.setPrivate(this, AbstractCard.class, "useSmallTitleFont", useSmallFont);
        }
    }
    
    protected void onInitialized()
    {
    }
    
    public void atTurnStartPreDraw()
    {
        String extra = getTemporaryExtraDescription();
        rawDescription = getFullDescription() + (extra != null ? (" NL " + extra) : "");
        initializeDescription();
    }
    
    public void applyPowers()
    {
        super.applyPowers();
        
        String extra = getTemporaryExtraDescription();
        rawDescription = getFullDescription() + (extra != null ? (" NL " + extra) : "");
        initializeDescription();
    }
    
    public void onMoveToDiscard()
    {
        rawDescription = getFullDescription();
        initializeDescription();
    }
    
    //region card parameters
    public abstract String getImagePath();
    
    //x-cost is -1, unplayable is -2
    public abstract int getCost();
    
    //this should be sealed and just get the ID based on the mod name + class name
    public abstract String getId();
    
    public String getDisplayName()
    {
        if (cardStrings != null)
        {
            return cardStrings.NAME;
        }
        else
        {
            return "(unnamed)";
        }
    }
    
    public String getBaseDescription()
    {
        return null;
    }
    
    public String getFullDescription()
    {
        String baseDescription = getBaseDescription();
        if ((baseDescription == null) || (Settings.language != Settings.GameLanguage.ENG))
        {
            //this means we didn't want to change the description. just use the old card strings system instead.
            
            if (cardStrings == null)
            {
                return "(unable to find card description.)";
            }
            else if (upgraded && !BcUtility.isNullEmptyOrWhiteSpace(cardStrings.UPGRADE_DESCRIPTION))
            {
                return cardStrings.UPGRADE_DESCRIPTION;
            }
            else
            {
                return cardStrings.DESCRIPTION;
            }
        }
        else
        {
            //build a description based on their properties + the base description.
            String fullDescription = baseDescription;
            String line1 = "";
            
            if (getRetain())
            {
                line1 += "Retain. ";
            }
            
            if (getInnate())
            {
                line1 += "Innate. ";
            }
            
            if (getEthereal())
            {
                line1 += "Ethereal. ";
            }
            
            if (!line1.equals(""))
            {
                line1 += "NL ";
            }
            
            fullDescription = line1 + fullDescription;
            
            if (getExhaust())
            {
                fullDescription += " NL Exhaust.";
            }
            
            return fullDescription;
        }
    }
    
    public String getTemporaryExtraDescription()
    {
        return "";
    }
    
    public abstract CardType getCardType();
    
    public final CardColor getCardColor()
    {
        String imgPath = getImagePath().toLowerCase(Locale.ROOT);
        if (imgPath.startsWith("red"))
        {
            return CardColor.RED;
        }
        else if (imgPath.startsWith("green"))
        {
            return CardColor.GREEN;
        }
        else if (imgPath.startsWith("blue"))
        {
            return CardColor.BLUE;
        }
        else if (imgPath.startsWith("purple"))
        {
            return CardColor.PURPLE;
        }
        else if (imgPath.startsWith("curse"))
        {
            return CardColor.CURSE;
        }
        else if (imgPath.startsWith("status"))
        {
            return CardColor.COLORLESS;
        }
        else
        {
            return CardColor.COLORLESS;
        }
    }
    
    public abstract CardRarity getCardRarity();
    
    public abstract CardTarget getCardTarget();
    
    public DamageInfo.DamageType getDamageType()
    {
        return DamageInfo.DamageType.NORMAL;
    }
    
    public int getBlock()
    {
        return -1;
    }
    
    public int getDamage()
    {
        return -1;
    }
    
    public boolean isAoeAttack()
    {
        return false;
    }
    
    public int getMagicNumber()
    {
        return -1;
    }
    
    public boolean getExhaust()
    {
        return false;
    }
    
    public boolean getRetain()
    {
        return false;
    }
    
    public boolean getInnate()
    {
        return false;
    }
    
    public boolean getEthereal()
    {
        return false;
    }
    //endregion
    
    public final void upgrade()
    {
        if (!upgraded)
        {
            int originalCost = getCost();
            int originalDamage = getDamage();
            int originalBlock = getBlock();
            int originalMagicNumber = getMagicNumber();
            
            upgradeName();
            
            int newCost = getCost();
            int newDamage = getDamage();
            int newBlock = getBlock();
            int newMagicNumber = getMagicNumber();
            
            if (originalCost != newCost)
            {
                upgradeBaseCost(newCost);
            }
            
            if (originalDamage != newDamage)
            {
                baseDamage = newDamage;
                upgradedDamage = true;
            }
            
            if (originalBlock != newBlock)
            {
                baseBlock = newBlock;
                upgradedBlock = true;
            }
            
            if (originalMagicNumber != newMagicNumber)
            {
                baseMagicNumber = newMagicNumber;
                magicNumber = baseMagicNumber;
                upgradedMagicNumber = true;
            }
            
            exhaust = getExhaust();
            selfRetain = getRetain();
            isEthereal = getEthereal();
            isInnate = getInnate();
            
            onUpgraded();
            
            rawDescription = getFullDescription();
            initializeDescription();
        }
    }
    
    protected void onUpgraded()
    {
    
    }
    
    public boolean isGlowingGold()
    {
        return false;
    }
    
    public final void triggerOnGlowCheck()
    {
        if (defaultGlow == null)
        {
            defaultGlow = glowColor;
        }
        glowColor = isGlowingGold() ? AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy() : defaultGlow;
    }
}
