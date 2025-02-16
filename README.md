# DAR Chatbot User Guide

Welcome to the DAR Chatbot! This guide will help you understand how to interact with the chatbot and make the most out of its features.

## Table of Contents
1. [Getting Started](#getting-started)
2. [Basic Commands](#basic-commands)
3. [Task Management](#task-management)
4. [Exiting the Chatbot](#exiting-the-chatbot)
5. [Error Handling](#error-handling)

## Getting Started

To start using the DAR Chatbot, simply run the application. You will be greeted with a welcome message and instructions on how to use the chatbot.

## Basic Commands

The DAR Chatbot understands several basic commands to help you manage your tasks. Here are some of the basic commands you can use:

- `todo <description>`: Adds a new ToDo task with the given description.
- `deadline <description> /by <date and/or time>`: Adds a new Deadline task with the given description and deadline.
- `event <description> /at <date and/or time>`: Adds a new Event task with the given description and event time.

## Task Management

### Adding a ToDo Task

To add a ToDo task, use the following command:
```
todo Buy milk
```
### Adding a Deadline Task

To add a Deadline task, use the following command:
```
deadline Submit assignment by 20/10/2023 2359
```
### Adding an Event Task

To add an Event task, use the following command:
```
event Team meeting from Monday to Tuesday
```
### Viewing Tasks

To view all tasks, use the `list` command:
```
list
```
This will display a list of all tasks with their status (done or not done).

### Marking and Unmarking Tasks

To mark or unmark a task, use the `mark` and `unmark`  command respectively followed by the task number:
```
mark 2
```
This will mark the second task in the list as done.

### Deleting Tasks

To delete a task, use the `delete` command followed by the task number:
```
delete 3
```
This will delete the third task in the list.

## Saving and Loading Tasks

The DAR Chatbot automatically saves your tasks to a file called `tasks.txt`. When you start the chatbot, it will load the tasks from this file.

## Exiting the Chatbot

To exit the chatbot, use the `bye` command:
```
bye
```

This will save your tasks and exit the application.

## Error Handling

If you enter an invalid command or provide incorrect input, the DAR Chatbot will display an error message. Make sure to follow the correct command format as described in this guide.

---

Thank you for using the DAR Chatbot! I hope this guide helps you manage your tasks effectively. If you have any questions or need further assistance, feel free to reach out to me!