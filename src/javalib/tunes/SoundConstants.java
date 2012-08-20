package javalib.tunes;

/**
 * Copyright 2009 Viera K. Proulx
 * This program is distributed under the terms of the 
 * GNU Lesser General Public License (LGPL)
 */

/**
 * <p>A collection of constants to use when defining musical instruments and notes
 * to play with the tunes library.</p>
 * <p>The interface also defines the mapping between the channels and instruments
 * that is used to initialize the <code>MIDI</code> channels.</p>
 * 
 * @author Viera K. Proulx
 * @since 29 October 2009, 9 June 2010
 */
public interface SoundConstants{

	/** names for the default channel assignments */
	  int PIANO = 0;
	  int ORGAN =  1;
	  int TIMPANI =  2;
	  int VIOLIN =  3;
	  int CLARINET =  4;
	  int STEELDRUM =  5;
	  int CHOIR = 6;
	  int TUBA = 7;
	  int SAX = 8;
	  int PERCUSSION = 9;
	  int WOOD_BLOCK =  10;
	  int BAGPIPE =  11;
	  int BIRD_TWEET =  12;
	  int SEASHORE = 13;
	  int APPLAUSE =  14;
	  int BRASS_SECTION =  15;
	  
	 
	  /** we will use the same note velocity throughout */
	  int NOTE_VELOCITY = 60;
	  int QUARTER_NOTE = 60;
	  
	  // MIDI instruments
	  /** the defualt instrument */
    int Piano = 1;
    
	  // Piano:
	  int AcousticGrandPiano = 1;
	  int BrightAcousticPiano = 2;
	  int ElectricGrandPiano = 3;
	  int HonkyTonkPiano = 4;
	  int ElectricPiano1 = 5;
	  int ElectricPiano2 = 6;
	  int Harpsichord = 7;
	  int Clavinet = 8;

	  // ChromaticPercussion:
	  int Celesta = 9;
	  int Glockenspiel = 10;
	  int MusicBox = 11;
	  int Vibraphone = 12;
	  int Marimba = 13;
	  int Xylophone = 14; 
	  int TubularBells = 15;
	  int Dulcimer = 16; 

	  // Organ:
	  int DrawbarOrgan = 17;
	  int PercussiveOrgan = 18;
	  int RockOrgan = 19;
	  int ChurchOrgan = 20;
	  int ReedOrgan = 21;
	  int Accordion = 22;
	  int Harmonica = 23;
	  int TangoAccordion = 24;

	  // Guitar: 
	  int AcousticGuitar_nylon = 25;
	  int AcousticGuitar_steel = 26;
	  int ElectricGuitar_jazz = 27;
	  int ElectricGuitar_clean = 28;
	  int ElectricGuitar_muted = 29;
	  int OverdrivenGuitar = 30;
	  int DistortionGuitar = 31;
	  int GuitarHarmonics = 32;

	  // Bass:
	  int AcousticBass = 33;
	  int ElectricBass_finger = 34;
	  int ElectricBass_pick = 35;
	  int FretlessBass = 36;
	  int SlapBass1 = 37;
	  int SlapBass2 = 38;
	  int SynthBass1 = 39;
	  int SynthBass2 = 40;

	  // Strings:
	  int Violin = 41;
	  int Viola = 42;
	  int Cello = 43;
	  int Contrabass = 44;
	  int TremoloStrings = 45;
	  int PizzicatoStrings = 46;
	  int OrchestralHarp = 47;
	  int Timpani = 48;

	  // Ensemble:
	  int StringEnsemble1 = 49;
	  int StringEnsemble2 = 50;
	  int SynthStrings1 = 51;
	  int SynthStrings2 = 52;
	  int ChoirAahs = 53;
	  int VoiceOohs = 54;
	  int SynthChoir = 55;
	  int OrchestraHit = 56;
	  
	  // Brass:
	  int Trumpet = 57;
	  int Trombone = 58;
	  int Tuba = 59;
	  int MutedTrumpet = 60;
	  int FrenchHorn = 61;
	  int BrassSection = 62;
	  int SynthBrass1 = 63;
	  int SynthBrass2 = 64;
	  
	  // Reed:
	  int SopranoSax = 65;
	  int AltoSax = 66;
	  int TenorSax = 67;
	  int BaritoneSax = 68;
	  int Oboe = 69;
	  int EnglishHorn = 70;
	  int Bassoon = 71;
	  int Clarinet = 72;

	  // Pipe:
	  int Piccolo = 73;
	  int Flute = 74;
	  int Recorder = 75;
	  int PanFlute = 76;
	  int BlownBottle = 77;
	  int Shakuhachi = 78;
	  int Whistle = 79;
	  int Ocarina = 80;

	  // Synth Lead:
	  int Lead1_square = 81;
	  int Lead2_sawtooth = 82;
	  int Lead3_calliope = 83;
	  int Lead4_chiff = 84;
	  int Lead5_charang = 85;
	  int Lead6_voice = 86;
	  int Lead7_fifths = 87;
	  int Lead8_bass_and_lead = 88;

	  // Synth Pad:
	  int Pad1_newAge = 89;
	  int Pad2_warm = 90;
	  int Pad3_polysynth = 91;
	  int Pad4_choir = 92;
	  int Pad5_bowed = 93;
	  int Pad6_metallic = 94;
	  int Pad7_halo = 95;
	  int Pad8_sweep = 96;

	  // Synth Effects:
	  int FX1_rain = 97;
	  int FX2_soundtrack = 98;
	  int FX3_crystal = 99;
	  int FX4_atmosphere = 100;
	  int FX5_brightness = 101;
	  int FX6_goblins = 102;
	  int FX7_echoes = 103;
	  int FX8_sci_fi = 104;

	  // Ethnic:
	  int Sitar = 105;
	  int Banjo = 106;
	  int Shamisen = 107;
	  int Koto = 108;
	  int Kalimba = 109;
	  int BagPipe = 110;
	  int Fiddle = 111;
	  int Shanai = 112;

	  // Percussive:
	  int TinkleBell = 113;
	  int Agogo = 114;
	  int SteelDrums = 115;
	  int Woodblock = 116;
	  int TaikoDrum = 117;
	  int MelodicTom = 118;
	  int SynthDrum = 119;

	  // Sound effects:
	  int ReverseCymbal = 120;
	  int GuitarFretNoise = 121;
	  int BreathNoise = 122;
	  int Seashore = 123;
	  int BirdTweet = 124;
	  int TelephoneRing = 125;
	  int Helicopter = 126;
	  int Applause = 127;
	  int Gunshot = 128;
	  
	  String[] instrumentNames = new String[]{
	  "Piano",
	  "AcousticGrandPiano",
	  "BrightAcousticPiano",
      "ElectricGrandPiano",
      "HonkyTonkPiano",
      "ElectricPiano1",
      "ElectricPiano2",
      "Harpsichord",
      "Clavinet",

	  // ChromaticPercussion:
	  "Celesta",
      "Glockenspiel",
      "MusicBox",
      "Vibraphone",
      "Marimba",
      "Xylophone",
      "TubularBells",
      "Dulcimer", 

	  // Organ:
	  "DrawbarOrgan",
      "PercussiveOrgan",
      "RockOrgan",
      "ChurchOrgan",
      "ReedOrgan",
      "Accordion",
      "Harmonica",
      "TangoAccordion",

	  // Guitar: 
	  "AcousticGuitar_nylon",
      "AcousticGuitar_steel",
      "ElectricGuitar_jazz",
      "ElectricGuitar_clean",
      "ElectricGuitar_muted",
      "OverdrivenGuitar",
      "DistortionGuitar",
      "GuitarHarmonics",

	  // Bass:
	  "AcousticBass",
      "ElectricBass_finger",
      "ElectricBass_pick",
      "FretlessBass",
      "SlapBass1",
      "SlapBass2",
      "SynthBass1",
      "SynthBass2",

	  // Strings:
	  "Violin",
      "Viola",
      "Cello",
      "Contrabass",
      "TremoloStrings",
      "PizzicatoStrings",
      "OrchestralHarp",
      "Timpani",

	  // Ensemble:
	  "StringEnsemble1",
      "StringEnsemble2",
      "SynthStrings1",
      "SynthStrings2",
      "ChoirAahs",
      "VoiceOohs",
      "SynthChoir",
      "OrchestraHit",
	    
	  // Brass:
	  "Trumpet",
      "Trombone",
      "Tuba",
      "MutedTrumpet",
      "FrenchHorn",
      "BrassSection",
      "SynthBrass1",
      "SynthBrass2",
	    
	  // Reed:
	  "SopranoSax",
      "AltoSax",
      "TenorSax",
      "BaritoneSax",
      "Oboe",
      "EnglishHorn",
      "Bassoon",
      "Clarinet",

	  // Pipe:
	  "Piccolo",
      "Flute",
      "Recorder",
      "PanFlute",
      "BlownBottle",
      "Shakuhachi",
      "Whistle",
      "Ocarina",

	  // Synth Lead:
	  "Lead1_square",
      "Lead2_sawtooth",
      "Lead3_calliope",
      "Lead4_chiff",
      "Lead5_charang",
      "Lead6_voice",
      "Lead7_fifths",
      "Lead8_bass_and_lead",

	  // Synth Pad:
	  "Pad1_newAge",
      "Pad2_warm",
      "Pad3_polysynth",
      "Pad4_choir",
      "Pad5_bowed",
      "Pad6_metallic",
      "Pad7_halo",
      "Pad8_sweep",

	  // Synth Effects:
	  "FX1_rain",
      "FX2_soundtrack",
      "FX3_crystal",
      "FX4_atmosphere",
      "FX5_brightness",
      "FX6_goblins",
      "FX7_echoes",
      "FX8_sci_fi",

	  // Ethnic:
	  "Sitar",
      "Banjo",
      "Shamisen",
      "Koto",
      "Kalimba",
      "BagPipe",
      "Fiddle",
      "Shanai",

	  // Percussive:
	  "TinkleBell",
      "Agogo",
      "SteelDrums",
      "Woodblock",
      "TaikoDrum",
      "MelodicTom",
      "SynthDrum",

	  // Sound effects:
	  "ReverseCymbal",
      "GuitarFretNoise",
      "BreathNoise",
      "Seashore",
      "BirdTweet",
      "TelephoneRing",
      "Helicopter",
      "Applause",
      "Gunshot"
	  };
	

    /** default mapping of channels to instruments they represent */
    int[] instruments = new int[]{
      Piano,
      ChurchOrgan,
      Timpani,
      Violin,
      Clarinet,
      SteelDrums,
      ChoirAahs,
      Tuba,
      AltoSax,
      PERCUSSION,
      Woodblock,
      BagPipe,
      BirdTweet,
      Seashore,
      Applause,
      BrassSection};
    
    int noteDownC  = 48;
    int noteDownCp = 49;
    int noteDownD  = 50;
    int noteDownDp = 51;
    int noteDownE  = 52;
    int noteDownF  = 53;
    int noteDownFp = 54;
    int noteDownG  = 55;
    int noteDownGp = 56;
    int noteDownA  = 57;
    int noteDownAp = 58;
    int noteDownB  = 59;
    int noteC  = 60;
    int noteCp = 61;
    int noteD  = 62;
    int noteDp = 63;
    int noteE  = 64;
    int noteF  = 65;
    int noteFp = 66;
    int noteG  = 67;
    int noteGp = 68;
    int noteA  = 69;
    int noteAp = 70;
    int noteB  = 71;
    int noteUpC  = 72;
    int noteUpCp = 73;
    int noteUpD  = 74;
    int noteUpDp = 75;
    int noteUpE  = 76;
    int noteUpF  = 77;
    int noteUpFp = 78;
    int noteUpG  = 79;
    int noteUpGp = 80;
    int noteUpA  = 81;
    int noteUpAp = 82;
    int noteUpB  = 83;
}