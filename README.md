<h1 align="center">Blog Explorer</h1>

[![PRs status|Solid](images/PRs-welcome-brightgreen.svg)]() [![Build Status](images/badge.svg)]()
## _MVVM based android application_


Blog explorer is a native android application written in kotlin .It is based on Model, View, ViewModel architecture. 

It uses [JSONPlaceholder] as API for testing this application. 

## Features

- Fetches Blog posts from an API.
- Displays the blog post's heading and brief description about it as a list.
- Each post can be opened to show a detailed view of it.
- Contains Heading, description and details about the author (Name, Username, Email and Website)
- Each blog post can be edited through an edit button on the corner. 

## Application structure
This application contains 3 activities, each with a specific layout.
1. Main Activity - This holds a recycler view with a list of all the blog posts. It contains the heading and description of the posts.
<p align="center">
  <img src="/images/main_activity.png" width=33% height=33%>
  </p>

2. Detail Activity - This Contains details of the post with text views of Post Id, Title, Body and Author details.
<p align="center">
<img src="/images/detail_activity.png" width=33% height=33%>
</p>

3. Edit Activity - This contains an interface for editing the blog posts with buttons to update and delete the blog post.
<p align="center">
<img src="/images/edit_activity.png" width=33% height=33%>
</p>

## Installation

It can be installed either through the apk package provided in the repository or by building the project from source in your android studio.

#### Building from source
> Copy link to this repository -> Open android studio ->
File -> New -> Project from version control -> Paste the url -> Clone

#### Apk download
Click [here] to download the apk or download it from the repository above.



## Development

Want to contribute? Great!

Blog explorer is written in Kotlin. Check the issues tab of this repository for any new issues you can contribute towards.

Clone this repository in your android studio and start working on any issue, after it is assigned to you.

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

[here]: </Apk/BlogExplorer.apk>

[JSONPlaceholder]: <https://jsonplaceholder.typicode.com>