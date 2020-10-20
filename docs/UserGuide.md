---
layout: page
title: User Guide
---

McGymmy (MG) is a **desktop app for Software Engineers who need help managing their diet, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). While we are primarily targetting software engineers who are familiar with the CLI, those who spend a large amount of time at their desk can still benefit from this application by saving time as opposed to the more traditional caloric tracker applications. <br>
If you can type fast, McGymmy can log your food intake faster than traditional GUI apps. MG aims to help users track their caloric and macronutrient intake in an efficient way that does not interfere with one's workflow.

This document aims to showcase all of MG's commands.
Open this document in a modern internet browser (Mozilla Firefox, Google Chrome, or Microsoft Edge).

##  Icon Legend
<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** This indicates that the following text consists of tips to better utilise MG

</div>
:information_source:  :This indicates important notes for current feature we are looking at<br>

## A little note from the developers

Thank you for using MG. We sincerely hope that MG plays apart to help you achieve a healthier lifestyle.
We understand that MG may be a little different from traditional GUI applications and it may be a little difficult to start using it initially.
Thus we recommend you to start by first understanding how MG works at a high level at the Quick start section. Then, explore the Features section.

<div markdown="block" class="alert alert-info">

:bulb: **Tip:** Press Ctrl-F to open the find prompt in your browser. You can type in keywords such as `add` or `edit` to quickly navigate to those parts of the document.

</div>

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

Double click on the jar file to open the GUI.
Type the command in the command box and press Enter to execute it. e.g. typing help and pressing Enter will open the help window.
Refer to the features below for details of each command.


1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `mcgymmy.jar` from [here](https://github.com/AY2021S1-CS2103T-W17-3/tp).

1. Copy the file to the folder you want to use as the _home folder_ for your McGymmy.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all food items.

   * **`add`**`-n potato -p 100 -c 5 -f 0` : Adds a food item named `potato` with `100`g of proteins, `5`g of carbs, and `0`g of fats into current date.

   * **`delete`**`3` : Deletes the 3rd food item (i.e. food item with index 3) shown in the current list.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

A typical *command* in *McGymmy* consists of a *Command Word* followed by several *parameters*.

For example in `add -n scrambled eggs -p 100`, `add` is a *Command Word* and `-n scrambled eggs` and `-p 100` are *parameters*.

**How to intepret the each command's format**:

We will follow the following convention for each command's format:

`COMMAND_WORD PARAMETERS [OPTIONAL_PARAMETERS]`, e.g. `add -n NAME [-p PROTEIN] [-f FATS] [-c CARBS] [-d DATE]`.

* `COMMAND_WORD` is the name of the command to be executed, and is always the first word in the *command*.

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add -n NAME -p PROTEIN`, `NAME` and `PROTEIN` are parameters which can be used as `add bacon -p 200`.

* Items in square brackets are optional.<br>
  e.g `-n NAME [-f FATS]` can be used as `-n bacon -f 10` or as `-n bacon`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[commnand;]…​` can be used as ` ` (i.e. 0 times), `delete 1;`, `delete 2; delete 1;` etc.

* Parameters and optional parameters can be in any order.<br>
  e.g. if the command specifies `-c CARBS -p PROTEIN [-f FATS]`, `-p PROTEIN [-f FATS] -c CARBS` is also acceptable.

</div>

### Viewing help : `help`

Displays information on the available commands in the terminal.

Format: `help [COMMAND]`

Examples:
* `help` - this will display all available commands.
* `help add` - this will display a help message specific to the `add` command.

![help message](images/CommandImagesForUG/Help.png)

### Adding a food item: `add`

Adds a food item to McGymmy.

Format: `add -n NAME [-p PROTEIN] [-f FATS] [-c CARBS] [-d DATE]`

Examples:
* `add -n potato -p 100 -c 5 -f 0`
* `add -n McSpicy`
* `add -n Wonton Mee -c 10`
* `add -n Sushi -d 20/04/2020`

:information_source:  The default value for protein, fats and carbs is 0. The default date is the day in which the food item is added.<br>

:information_source:  To view the list of supported date formats, see *Appendix A*.<br>

![add command example](images/CommandImagesForUG/Add.png)

### Clearing all food items : `clear`

Removes all food items in McGymmy.

* Items are removed permanently.
* All additional input after the *command word* `clear` will be ignored. E.g. `clear` and `clear 123` will have the same effect.

Format: `clear`

![clear command example](images/CommandImagesForUG/Clear.png)

### Tagging food items : `tag`

Tags a food item in McGymmy.

* Tags `TAG_NAME` for food item at the specified `INDEX`.
* The index refers to the index number shown in the displayed food list.
* The index **must be a positive integer** 1, 2, 3, …​

Format: `tag INDEX -t TAG_NAME`

![tag command example](images/CommandImagesForUG/Tag.png)

### Tagging food items : `untag`

Untags a food item in McGymmy.

* Untags `TAG_NAME` for food item at the specified `INDEX`.
* The index refers to the index number shown in the displayed food list.
* The index **must be a positive integer** 1, 2, 3, …​

Format: `untag INDEX -t TAG_NAME`

![untag command example](images/CommandImagesForUG/Untag.png)

### Finding a food item: `find`

Finds food items based on the keywords supplied.

* Filters the displayed list of food items to only include food items containing the supplied keywords.

Format: `find KEYWORDS`

![find_command_example](images/CommandImagesForUG/Find.png)

### Listing all food items : `list`

Shows a list of all food items in McGymmy.

* Lists all food items in McGymmy
* All additional input after the *command word* `list` will be ignored. E.g. `list` and `list 123` will have the same effect.

Format: `list`

![list command example](images/CommandImagesForUG/List.png)

### Editing a food item : `edit`

Edits the details of a food item at the specified index.

Format: `edit INDEX [-n NAME] [-p PROTEIN] [-c CARBS] [-f FATS] [-d DATE]`

Examples: 

* Edits the food item at the specified `INDEX`. The index refers to the index number shown in the displayed food list.
* The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 3 -n banana -p 120` Changes the `name` and `protein` values of the 3rd item in the list to `banana` and `120` respectively.

:information_source:  To view the list of supported date formats, see *Appendix A*.<br>

![edit command example](images/CommandImagesForUG/Edit.png)

### Deleting a food item: `delete`

Deletes a food item at the specified index.

Format: `delete INDEX`

* Deletes the food at the specified `INDEX`.
* The index refers to the index number shown in the displayed food list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd food item in McGymmy.

![delete command example](images/CommandImagesForUG/Delete.png)

### Importing another save file: `import`

Imports a previously saved file into McGymmy

Format: `import FILEPATH`

* Imports the saved file at `FILEPATH`

Examples:
* `import c:/mcgymmy/save_file.json` imports `save_file.json` into McGymmy

![Import command example](images/CommandImagesForUG/Import.png)

For those who are more inclined to use a gui to import:
* Click on file
* Click on Import
* Navigate using the GUI to the save file

![GUI import command example](images/CommandImagesForUG/ImportGUI.png)

### Exporting your save file to a folder `export`

Imports a previously saved file into McGymmy

Format: `import DIRPATH [-o FILENAME]`

* Exports the saved file to `DIRPATH` with `FILENAME`
* Default filename is `mcgymmy.json`

Examples:
* `export c:/mcgymmy` exports the save file as `mcgymmy.json` to `c:/mcgymmy`
* `export c:/mcgymmy -o save_file` exports the save file as `save_file.json` to `c:/mcgymmy`

![Export command example](images/CommandImagesForUG/Export.png)

For those who are more inclined to use a gui to export:
* Click on file
* Click on Export
* Navigate using the GUI to the save file

![GUI export command example](images/CommandImagesForUG/ExportGUI.png)


### Exiting the program : `exit`

Exits the program.

* Exits McGymmy and closes the McGymmy window.
* Data will be automatically saved (see also section on *Saving the data* below).
* All additional input after the *command word* `exit` will be ignored. E.g. `exit` and `exit 123` will have the same effect.

Format: `exit`


### Creating a macro command : `macro`

Adds a macro to run several commands in succession.

*__WARNING:__* this is an advanced feature!

Arguments to the macro can be substituted in the commands using the syntax:
\ARGUMENT_NAME.

Unnamed arguments can be substituted using the syntax: \$

Format: `macro SHORTCUT ARGUMENT_1 ARGUMENT_2 ... ; COMMAND_1 ARGUMENTS_TO_COMMAND \REUSED_ARGUMENT; [COMMAND_2; ...]` <br>

* Create a macro with name `SHORTCUT` and arguments `ARGUMENT_1` and `ARGUMENT_2` which executes `COMMAND_1; COMMAND_2; ...`.

Examples:
* `macro addWith100cal p ; add -n \$ -c 100 -p \p`
    * Example usage of this macro: `addWith100cal Banana -p 2000`
    * The following command will be executed by the macro: `add -n Banana -c 100 -p 2000`
    * i.e. in `add -n \$ -c 100 -p \p`, `\$` and `\p` will be substituted with Banana and 2000 respectively.
    
* `macro addFoodWithFries; add -n \$ ; add -n \$ With Fries`
    * Example usage of this macro: `addFoodWithFries Ice Cream`
    * The following commands will be executed by the macro: `add -n Ice Cream` and `add -n Ice Cream With Fries`.

![Macro command example](images/CommandImagesForUG/Macro.png)


### Saving the data

McGymmy's data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous McGymmy home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|------------------
**Add**    | `add -n NAME [-p PROTEIN] [-f FATS] [-c CARBS] [-d DATE]` <br> e.g., `add Chicken Rice -p 10 -f 5 -c 23 -d 02/09/2020`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [-n NAME] [-p PROTEIN] [-f FATS] [-c CARBS] [-d DATE]`<br> e.g.,`edit 2 -n Chicken Rice -p 30 -f 50 -c 60 -d 02/09/2020`
**Exit**   | `exit`
**Export** | `import DIRPATH [-o FILENAME]` <br> e.g., `export c:/mcgymmy -o save_file`
**Find**   | `find KEYWORDS` <br> e.g., `find chicken`
**Help**   | `help [COMMAND]` <br> e.g., `help add`
**Import** | `macro SHORTCUT; COMMAND_1; [COMMAND_2;] …​` <br> e.g., `import c:/mcgymmy/save_file.json`
**List**   | `list`
**Macro**  | `macro SHORTCUT; COMMAND_1; [COMMAND_2;] …​` <br> e.g., `macro lunch; add Chicken`
**Tag**    | `tag INDEX -t TAG_NAME` <br> e.g., `tag 1 -t Lunch`
**UnTag**  | `untag INDEX -t TAG_NAME` <br> e.g., `untag 1 -t Lunch`

## Appendix A
List of supported input date formats, sorted from highest parsing priority to lowest parsing priority

Format       | Example
------------ | --------
*yyyy-MM-dd* | 2020-09-02
*yyyy-M-dd*  | 2020-9-02
*yyyy-M-d*   | 2020-9-2
*dd-MM-yyyy* | 02-09-2020
*dd-M-yyyy*  | 02-9-2020
*d-M-yyyy*   | 2-9-2020
*yyyy/MM/dd* | 2020/09/02
*dd/MM/yyyy* | 02/09/2020
*dd/M/yyyy*  | 02/9/2020
*d/M/yyyy*   | 2/9/2020
*d MMM yyyy* | 2 Sep 2020
