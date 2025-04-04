//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

public class Healer extends AbstractMonster
{
    public static final String ID = "Healer";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    private static final float IDLE_TIMESCALE = 0.8F;
    public static final String ENC_NAME = "HealerTank";
    private static final int HP_MIN = 48;
    private static final int HP_MAX = 56;
    private static final int A_2_HP_MIN = 50;
    private static final int A_2_HP_MAX = 58;
    private static final int MAGIC_DMG = 8;
    private static final int HEAL_AMT = 16;
    private static final int STR_AMOUNT = 2;
    private static final int A_2_MAGIC_DMG = 9;
    private static final int A_2_STR_AMOUNT = 3;
    private int magicDmg;
    private int strAmt;
    private int healAmt;
    private int healThreshold;
    private static final byte ATTACK = 1;
    private static final byte HEAL = 2;
    private static final byte BUFF = 3;
    
    public Healer(float x, float y)
    {
        super(NAME, "Healer", 56, 0.0F, -20.0F, 230.0F, 250.0F, (String) null, x, y);
        if (AbstractDungeon.ascensionLevel >= 7)
        {
            setHp(44, 48);
        }
        else
        {
            setHp(42, 46);
        }
        
        if (AbstractDungeon.ascensionLevel >= 17)
        {
            magicDmg = 9;
            strAmt = 2;
            healAmt = 30;
            healThreshold = 30;
        }
        else if (AbstractDungeon.ascensionLevel >= 2)
        {
            magicDmg = 9;
            strAmt = 2;
            healAmt = 20;
            healThreshold = 20;
        }
        else
        {
            magicDmg = 8;
            strAmt = 2;
            healAmt = 15;
            healThreshold = 15;
        }
        
        damage.add(new DamageInfo(this, magicDmg));
        loadAnimation("images/monsters/theCity/healer/skeleton.atlas", "images/monsters/theCity/healer/skeleton.json", 1.0F);
        TrackEntry e = state.setAnimation(0, "Idle", true);
        stateData.setMix("Hit", "Idle", 0.2F);
        e.setTime(e.getEndTime() * MathUtils.random());
        state.setTimeScale(0.8F);
    }
    
    public void takeTurn()
    {
        Iterator var1;
        AbstractMonster m;
label35:
        switch (nextMove)
        {
            case ATTACK:
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo) damage.get(0), AttackEffect.SLASH_DIAGONAL));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, 2, true), 2));
                break;
            case HEAL:
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAFF_RAISE"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
                var1 = AbstractDungeon.getMonsters().monsters.iterator();
                
                while (true)
                {
                    if (!var1.hasNext())
                    {
                        break label35;
                    }
                    
                    m = (AbstractMonster) var1.next();
                    if (!m.isDying && !m.isEscaping)
                    {
                        if (m == this)
                        {
                            AbstractDungeon.actionManager.addToBottom(new HealAction(m, this, healAmt / 3));
                        }
                        else
                        {
                            AbstractDungeon.actionManager.addToBottom(new HealAction(m, this, healAmt));
                        }
                    }
                }
            case BUFF:
                playSfx();
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAFF_RAISE"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
                var1 = AbstractDungeon.getMonsters().monsters.iterator();
                
                while (var1.hasNext())
                {
                    m = (AbstractMonster) var1.next();
                    if (!m.isDying && !m.isEscaping)
                    {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, strAmt), strAmt));
                    }
                }
        }
        
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
    
    private void playSfx()
    {
        if (MathUtils.randomBoolean())
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_HEALER_1A"));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_HEALER_1B"));
        }
        
    }
    
    private void playDeathSfx()
    {
        int roll = MathUtils.random(2);
        if (roll == 0)
        {
            CardCrawlGame.sound.play("VO_HEALER_2A");
        }
        else if (roll == 1)
        {
            CardCrawlGame.sound.play("VO_HEALER_2B");
        }
        else
        {
            CardCrawlGame.sound.play("VO_HEALER_2C");
        }
        
    }
    
    public void changeState(String key)
    {
        byte var3 = -1;
        switch (key.hashCode())
        {
            case -1729868403:
                if (key.equals("STAFF_RAISE"))
                {
                    var3 = 0;
                }
            default:
                switch (var3)
                {
                    case 0:
                        state.setAnimation(0, "Attack", false);
                        state.setTimeScale(0.8F);
                        state.addAnimation(0, "Idle", true, 0.0F);
                    default:
                }
        }
    }
    
    protected void getMove(int num)
    {
        int missingTeamHealth = 0;
        
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters)
        {
            if (!monster.isDying && !monster.isEscaping)
            {
                missingTeamHealth += monster.maxHealth - monster.currentHealth;
            }
        }
        
        if (missingTeamHealth >= healThreshold)
        {
            setMove(HEAL, Intent.MAGIC);
        }
        else if (num >= 50)
        {
            setMove(ATTACK, Intent.ATTACK_DEBUFF, damage.get(0).base);
        }
        else if (!lastTwoMoves(BUFF))
        {
            setMove(BUFF, Intent.BUFF);
        }
        else
        {
            setMove(ATTACK, Intent.ATTACK_DEBUFF, damage.get(0).base);
        }
    }
    
    public void damage(DamageInfo info)
    {
        super.damage(info);
        if (info.owner != null && info.type != DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.setTimeScale(0.8F);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
        
    }
    
    public void die()
    {
        playDeathSfx();
        state.setTimeScale(0.1F);
        useShakeAnimation(5.0F);
        super.die();
    }
    
    static
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Healer");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
