package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;

public class ShelledParasite extends AbstractMonster
{
    public static final String ID = "Shelled Parasite";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final int HP_MIN = 68;
    private static final int HP_MAX = 72;
    private static final int A_2_HP_MIN = 70;
    private static final int A_2_HP_MAX = 75;
    private static final float HB_X_F = 20.0F;
    private static final float HB_Y_F = -6.0F;
    private static final float HB_W = 350.0F;
    private static final float HB_H = 260.0F;
    private static final int PLATED_ARMOR_AMT = 14;
    private static final int FELL_DMG = 18;
    private static final int DOUBLE_STRIKE_DMG = 6;
    private static final int SUCK_DMG = 10;
    private static final int A_2_FELL_DMG = 21;
    private static final int A_2_DOUBLE_STRIKE_DMG = 7;
    private static final int A_2_SUCK_DMG = 12;
    private int fellDmg;
    private int doubleStrikeDmg;
    private int suckDmg;
    private static final int DOUBLE_STRIKE_COUNT = 2;
    private static final int FELL_FRAIL_AMT = 2;
    private static final byte FELL = 1;
    private static final byte DOUBLE_STRIKE = 2;
    private static final byte LIFE_SUCK = 3;
    private static final byte STUNNED = 4;
    private boolean firstMove;
    public static final String ARMOR_BREAK = "ARMOR_BREAK";
    
    public ShelledParasite(float x, float y)
    {
        super(NAME, "Shelled Parasite", 72, 20.0F, -6.0F, 350.0F, 260.0F, null, x, y);
        firstMove = true;
        loadAnimation("images/monsters/theCity/shellMonster/skeleton.atlas", "images/monsters/theCity/shellMonster/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = state.setAnimation(0, (String) "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.2F);
        e.setTimeScale(0.8F);
        dialogX = -50.0F * Settings.scale;
        if (AbstractDungeon.ascensionLevel >= 7)
        {
            //setHp(70, 75);
            setHp(75, 82);
        }
        else
        {
            setHp(68, 72);
        }
        
        if (AbstractDungeon.ascensionLevel >= 2)
        {
            doubleStrikeDmg = 7;
            fellDmg = 21;
            suckDmg = 12;
        }
        else
        {
            doubleStrikeDmg = 6;
            fellDmg = 18;
            suckDmg = 10;
        }
        
        damage.add(new DamageInfo(this, doubleStrikeDmg));
        damage.add(new DamageInfo(this, fellDmg));
        damage.add(new DamageInfo(this, suckDmg));
    }
    
    public ShelledParasite()
    {
        this(-20.0F, 10.0F);
    }
    
    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 14)));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 14));
    }
    
    public void takeTurn()
    {
label18:
        switch (nextMove)
        {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(1), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
                break;
            case 2:
                int i = 0;
                
                while (true)
                {
                    if (i >= 2)
                    {
                        break label18;
                    }
                    
                    AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.2F));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                    ++i;
                }
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-25.0F, 25.0F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-25.0F, 25.0F) * Settings.scale, Color.GOLD.cpy()), 0.0F));
                AbstractDungeon.actionManager.addToBottom(new VampireDamageAction(AbstractDungeon.player, (DamageInfo) damage.get(2), AbstractGameAction.AttackEffect.NONE));
                break;
            case 4:
                setMove((byte) 1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(1)).base);
                AbstractDungeon.actionManager.addToBottom(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.STUNNED));
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    public void changeState(String stateName)
    {
        byte var3 = -1;
        switch (stateName.hashCode())
        {
            case 1280154047:
                if (stateName.equals("ARMOR_BREAK"))
                {
                    var3 = 1;
                }
                break;
            case 1941037640:
                if (stateName.equals("ATTACK"))
                {
                    var3 = 0;
                }
        }
        
        switch (var3)
        {
            case 0:
                state.setAnimation(0, (String) "Attack", false);
                state.addAnimation(0, (String) "Idle", true, 0.0F);
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
                AbstractDungeon.actionManager.addToBottom(new AnimateHopAction(this));
                setMove((byte) 4, AbstractMonster.Intent.STUN);
                createIntent();
        }
        
    }
    
    public void damage(DamageInfo info)
    {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, (String) "Hit", false);
            state.addAnimation(0, (String) "Idle", true, 0.0F);
        }
        
    }
    
    protected void getMove(int num)
    {
        if (firstMove)
        {
            firstMove = false;
            if (AbstractDungeon.ascensionLevel >= 17)
            {
                setMove((byte) 1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(1)).base);
            }
            else
            {
                if (AbstractDungeon.aiRng.randomBoolean())
                {
                    setMove((byte) 2, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(0)).base, 2, true);
                }
                else
                {
                    setMove((byte) 3, AbstractMonster.Intent.ATTACK_BUFF, ((DamageInfo) damage.get(2)).base);
                }
                
            }
        }
        else
        {
            if (num < 20)
            {
                if (!lastMove((byte) 1))
                {
                    setMove((byte) 1, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo) damage.get(1)).base);
                }
                else
                {
                    getMove(AbstractDungeon.aiRng.random(20, 99));
                }
            }
            else if (num < 60)
            {
                if (!lastTwoMoves((byte) 2))
                {
                    setMove((byte) 2, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(0)).base, 2, true);
                }
                else
                {
                    setMove((byte) 3, AbstractMonster.Intent.ATTACK_BUFF, ((DamageInfo) damage.get(2)).base);
                }
            }
            else if (!lastTwoMoves((byte) 3))
            {
                setMove((byte) 3, AbstractMonster.Intent.ATTACK_BUFF, ((DamageInfo) damage.get(2)).base);
            }
            else
            {
                setMove((byte) 2, AbstractMonster.Intent.ATTACK, ((DamageInfo) damage.get(0)).base, 2, true);
            }
            
        }
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Shelled Parasite");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
