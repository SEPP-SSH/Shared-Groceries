# Assignment Report
---
## What we accomplished
We selected Harvey’s EDR to prototype, and subsequently implemented – for Student Smart Homes (SSH) – a group grocery shopping feature in the SSH Android App, a supporting request handling server in SSH Cloud, which itself draws on a MySQL database server.

The prototype allows a user that’s a part of a shared house to contribute to group grocery orders with partner supermarkets. The user can choose which supermarket to place an order with, and place orders with more than one supermarket at a given time. The user can browse for items to add to an order, change the quantity they’ve added in the basket, and outright remove them. The user cannot remove items added by other housemates, and pays only for the items that they added themselves. This prototype only simulates one user adding items at a given time, making the feature clearer to understand. Submitting a group order, payment splitting and payments processing have been omitted from the prototype, and are instead simulated by order submission always being accepted, with no additional processing. The EDR has also been altered to not implement item search functionality, nor the feature for the SSH Console table, and to omit the order deadline condition. 

## How we accomplished it
Most fundamental to how we achieved the implementation of our prototype was using a version control system. By using Git to track and manage commits, and organising them into branches, allowed us to divide ourselves into two teams – one to work on the SSH App facet of the prototype, and another to work on the SSH Cloud facet, which helped us demonstrate the viability of the group grocery order feature quicker. Further, we documented key expectations for external behaviours and communication standards between each aspect of the system – a method which rendered the process of integrating the two systems smooth and successful.

On top of this, we utilised GitHub to centralise our Git repository, to add GitHub Actions to implement continuous integration (CI) workflows for both the SSH App and SSH Cloud, and then branch protection rules on our main branch. By centralising our Git repository, we were able to utilise pull requests to manage branch merging in a collaborative manner, using reviews and comments to coordinate which branches should be merged and when. Using GitHub Actions allowed for the automated build, testing and build artifact extraction for both the SSH App and SSH Cloud systems, and further enabled us to require these Actions to pass for a branch to be merged into main, as per the branch protection rule. Our branch protection rule for the main branch also required at least one reviewer before merging, ensuring that new code is of sufficient quality before destructively altering the main branch. 
 
We also used GitHub’s project management tools to organise the prototype’s development, where GitHub issues – organised into milestones – were used to track the tasks that needed to be completed to achieve the prototype, ensuring we implemented all necessary components and fixed all identified bugs. These issues were further organised into a Kanban to help with visualising the status of the prototype. Comments on issues were crucial for quick and effective collaboration on how best to implement the trickier aspects of the system.

Turning to the technical management of the project, we used both Gradle and Maven build systems, in the SSH App and SSH Cloud respectively, to automatically resolve dependencies, manage their versions, build the applications, and run tests. This enabled us to focus on the higher-level product logic, rather than focussing on the minutia of Java compilation. Furthermore, the use of build systems allowed for easy collaboration on the projects, with minimal setup needed for each developer to resolve dependencies in projects.

Both of these projects within the prototype are built with Java, utilising Javalin for networking between them. In the SSH Cloud facet, Hibernate and the Javax Persistence API are used for Object/Relational Mapping (ORM), so that Java objects can be persisted and selected without us having to write SQL queries and commands. Jackson is also used to parse Java objects to JSON files, and vice versa, and Lombok is used for processing annotations in Java classes that represent database entities, stored in the MySQL server. The SSH App was developed in the Android Studio IDE which allowed us to utilise unit tests, create layout files using XML and deploy the app to various emulators.

To facilitate the SSH Cloud continuous integration workflows, we made use of Docker and docker-compose for containerisation and orchestration respectively, where the major components of the SSH Cloud product are split into different Docker containers, and then networked and started in the correct order using docker-compose. There are different containerisation structures and orchestration workflows for unit testing and integration testing, and leveraging this technique allowed us to easily create portable code with reproducible results, as well as easily allowing the Maven build system to run tests and build the project within the CI workflow, saving time on project management, and freeing up more time for debugging and further feature development, which helped us achieve the prototype in the limited time frame. Containerisation and networking in this manner also helped us develop the prototype in a more realistic fashion, capturing how the separation of concerns would be addressed in the development process of the full-feature, allowing us to further demonstrate the viability of the feature at production scale. 

Testing – both unit and integration – was implemented with the use of the JUnit library, where standard unit tests were run on the App when the Gradle project was requested to build, and run on the Cloud when the Maven project was requested to build. Integration tests apply primarily to the SSH Cloud facet, where JUnit is used to run a test in a minimal client application that verifies the correct results of network communication, request processing, database actions, and server-to-server networking, in aggregate. 

---
# Reflections
## George Henderson's Reflections
#### George's reflections on Parla
I enjoyed collaborating with Parla on this project. While she might have been quieter during initial project discussions, she definitely showed some willingness to contribute. Her contributions, such as writing unit tests for the SSH App, were valuable to the project. For future projects she could work improving her knowledge and understanding of GitHub and the git version control system as this is a highly important skill to have.

To further enhance her impact, I think Parla would benefit from sharing her ideas more often and contributing to the team’s decision-making process, for example, during group discussions, as everyone has a unique perspective and by combining these it can really improve the end result.

Additionally, I believe it would be beneficial for Parla to actively seek out specific tasks that align with her interests and skills. By taking ownership of particular components of the project, she can not only enhance her own learning experience and deepen her understanding of the project but also contribute meaningfully to the team's overall success. Furthermore, it would also likely make the project more enjoyable and engaging for her as she would be working on something that interests her.

Overall, I believe that with a bit more proactive engagement, Parla has the potential to be an even more valuable asset to future group projects. By taking the initiative to share her ideas, seek out specific tasks, and actively participate in discussions, she can significantly enhance her contributions and the overall quality of the team's work.

#### George's reflections on Harvey
Harvey demonstrated strong leadership skills and was instrumental in keeping the project on track. His clear communication and effective use of version control made project management significantly easier through the use of GitHub issues and informative commit descriptions.

To become an even more effective team member in the future, perhaps he could delegate tasks more effectively to other team members. By sharing the workload more evenly, utilising other team members with less work, he could have fostered a more collaborative environment. Having a more balanced distribution of work could allow others to further develop their skills and take on greater responsibility.

Harvey demonstrated a keen attention to detail, often referring back to the original EDR to ensure accuracy and consistency. His logical thinking and problem-solving skills were invaluable in identifying and resolving issues, particularly when working with Docker. Despite encountering challenges, Harvey persevered and successfully implemented the Docker environments, showcasing both his determination and technical ability.

Overall, it was a pleasure to work with Harvey and his technical skills and dedication to the project were commendable. He consistently provided helpful feedback and demonstrated a deep understanding of GitHub and the git version control system. His contributions significantly impacted the project's success.

#### George's reflections Hasan
Hasan was a valuable member of the team, contributing to the database component of the SSH Cloud. He also helped with testing the SSH Cloud and documenting the development of the added features.

To further improve his effectiveness as part of a team in the future, Hasan could improve his communication and collaboration skills. While his technical skills are strong, he could sometimes have been more open about his ideas and how he was approaching tasks. For example, he could have been more proactive in seeking feedback on his work, offering assistance to other team members if they struggle to run his code, and sharing his ideas in group discussions.

Additionally, Hasan could benefit from deepening his knowledge and understanding of GitHub and the git version control system. A stronger grasp of these tools would enable him to work more efficiently and collaboratively with the team. This involves ensuring to stick to branch naming conventions and reviewing/commenting on other’s work whether that’s through pull requests or in GitHub issues.

Overall, I enjoyed working with Hasan on this team project and his contributions were valuable but by addressing these areas, he can become an even more effective team member and contribute significantly to future projects.

---

## Parla Tellioglu's Reflections
#### Parla's reflections on George
George brought significant value to the project through consistent engagement and skillful contributions to the frontend of the SSH App, as well as ensuring functionality and effective communication with the SSH Cloud. His technical expertise was critical to achieving the project’s objectives and his friendly and collaborative attitude created a positive and supportive team dynamic. George actively participated in team discussions, demonstrating a strong understanding of the project’s requirements and offering thoughtful solutions to challenges. His willingness to assist others and share knowledge during meetings showcased his dedication to teamwork and his reliability. George’s ability to focus on complex tasks and deliver high quality work on time was a significant strength throughout the project.

While George made significant contributions that greatly benefited the entire group, clearer communication about the approach and the tasks would have allowed others to contribute more effectively. By sharing more details about the work being done and involving team members in the process, it could have been ensured that everyone had the opportunity to participate meaningfully and add value to the project. Greater collaboration during planning and implementation phases would empower others to take on responsibilities and contribute more actively to the team’s success.

His technical skills, along with his willingness to support others, make him a vital team member whose increased involvement into group processes will lead to even greater success in future projects.

#### Parla's reflections on Harvey
Harvey demonstrated exceptional leadership throughout the project, showing enthusiasm and commitment that motivated the entire team. His detailed planning and clear milestones provided structure and efficiency to the workflow. By managing the GitHub repository effectively and simplifying the EDR with attention to detail, he ensured that the project goals remained realistic and achievable. His ability to revise code and provide constructive feedback was invaluable. Harvey’s approach reflected a deep understanding of the project’s requirements, and his proactive mindset ensured the team stayed on schedule.

One area for improvement is the communication around role distributions. While Harvey took on a significant portion of the workload, a more balanced and transparent allocation of responsibilities could have ensured greater equity within the team. Encouraging broader involvement in task management would not only enhance the workload but also give other team members opportunities to develop leadership and technical skills. Additionally, fostering more open discussions about tasks and priorities would enhance overall collaboration. 

Harvey’s leadership set a strong foundation for success, and his strategic thinking and organizational abilities will continue to drive productive teamwork in the future.

#### Parla's reflections on Hasan
Hasan showed remarkable technical skill and discipline throughout the project, particularly in his work on the database. His structured and methodical approach ensured that critical components of the project were completed efficiently and to a high standard. Hasan’s deep technical knowledge was evident in his ability to address complex tasks with confidence.

Hasan’s impact on future projects could be strengthened through greater engagement in group discussions and more active communication. By leveraging his deep technical expertise and disciplined approach more vocally in group discussions, Hasan has the potential to elevate the entire team's performance to new levels. His insights and ideas, when shared more frequently, could spark different solutions and drive the project's direction in new ways.

Hasan’s technical expertise and disciplined approach make him a valuable asset to any team. By taking a more proactive stance in voicing his ideas, he will inspire and empower his teammates, fostering a more dynamic and collaborative environment.

---

## Harvey Nicholson's Reflections
#### Harvey's reflections on George
Throughout the project, you’ve shown excellent engagement, working both independently and collaboratively, adeptly switching style based on which was more appropriate in the moment. You’ve also demonstrated that you’re able to communicate effectively and frequently with teammates about what aspects of the system you’re working on, what challenges you’re facing, and what help you may need. Furthermore, you successfully collaborated with the team on setting a clear standard for what each aspect of the system was expected to receive and output in terms of data. These skills were key to enabling the success of the development of the prototype.

With respect to version control, you demonstrated sound use of the systems we agreed on for the prototype development, and in future, you could further refine your skills by making smaller, more frequent commits to your working branch, so that the effect of your changes is even clearer to your colleagues when reviewing your code.

When it comes to risk management, you critically evaluated the proposal in the Engineering Design Review (EDR), which led to the excellent evaluation of which aspects of the system needed developing to prove that the project is viable, and you could further expand on this by conducting more frequent assessments of whether your “development direction” aligns with the proposal in hand, so as to ensure that the feature/project doesn’t experience scope creep or drift.
Glancing at debugging, the pace at which you identified and rectified bugs within your code was excellent.

#### Harvey's reflections on Parla
Your engagement throughout this project has been somewhat lacking, and going forward you could take some initiative to indicate to your teammates which aspects of the system you believe you could add something to. This would both reduce the workload of your teammates and develop your own skills of collaborative project work.

Going forward, you could also make attempts to communicate with your colleagues, so that they are aware of what, if anything, you are currently working on, so that they can better identify how the project’s course will flow, track targets and milestones, and reach the project goals more quickly and effectively. Furthermore, you could also engage with your teammates’ pull requests, where reviewing and commenting on your colleagues’ work will help the team’s output be of a higher quality.

More generally, responding to your teammates’ attempts to communicate with you would be a great starting point moving forward.
Your use of software engineering techniques such as version control could be refined by following your organisation’s branch naming conventions, and adding descriptions to your commits and pull requests. Furthermore, with respect to testing, a more well-thought-out approach to writing tests would be a great improvement to your output. This would allow your work to convey itself more clearly in how it helps achieve the project goals.
In the future, you could take initiative in identifying what software engineering skills you bring to the team, and how best you can apply them to the project you’re working on.

#### Harvey's reflections on Hasan
Throughout the project, you’ve shown excellent skills of communication, working with your teammates to ensure that your work is moving in the same direction as the rest of the team. You’ve also shown good engagement, achieving the development of your work units in a timely manner, and you could further build on this by taking more initiative, and actively discussing with the team how you can help to achieve the project goals. 

With respect to your use of version control, you demonstrated excellent use of the tools we agreed upon for the project, making frequent and useful commits. To build further on this, your branch organisation could be improved by making sure that your branch names conform to organisation conventions, and by ensuring that your pull requests are well named and described, so that reviewers can gain some idea of what you worked on in that branch before digging into the detail of the line-by-line changes. 

With respect to pull requests, actively reviewing your teammates’ work will help them work more effectively, moving the team closer to the goal in a more efficient manner, and will also help you build further on your skills of critical analysis of code. 

Whilst your development skills are more than sound, in the future you could build more resilience in debugging your code, furthering your problem-solving skills, whilst avoiding the need for your teammates to defocus on their work, wherever possible and sensible.

---

## Hasan Alshaban's Reflections
#### Hasan's reflections on George
George played a key role in the SSH App team by building the app using Android Studio Ladybug. The SSH App is a critical part of the project, and George ensured that it was functional and able to communicate with the SSH Cloud effectively. Additionally, he worked on the app’s documentation, which was an important part of delivering a complete prototype. His contributions in this area were valuable to the overall success of the project.

George also collaborated with the SSH Cloud team by discussing the integration points where the SSH App depended on the SSH Cloud. These discussions helped ensure that the app’s requirements were clearly understood and implemented on the server side. In addition, George assisted with GitHub management, taking on tasks such as assigning issues and reviewing pull requests. His support in this area helped the overall project stay organized and maintain a smooth workflow.

George has a solid ability to work independently, learn new tools, and communicate ideas effectively. These traits contributed to the project's progress, especially in terms of keeping the SSH App development on track. However, I think he could benefit from engaging his teammate more and delegating tasks within his team. This would allow for a more balanced workload and ensure that everyone contributes to the app’s development.

Overall, George was a dependable team member, and I appreciated his contributions. I believe he has the skills and mindset to be a strong teammate in future projects.

#### Hasan's reflections on Parla
Parla was a member of the SSH App team and contributed by writing unit tests for the app. These tests are an important part of ensuring the app’s functionality and maintaining its reliability. While her contributions were not as extensive as others in this particular project, the work she did helped uphold the quality of the app.

It’s evident that Parla has the confidence and ability to do excellent work, and I believe she has the potential to make significant contributions to any project she’s involved in. For this project, the demands of the SSH App were high, and greater involvement from all team members could have made the workload easier to manage and led to even stronger outcomes.

In the future, I think Parla could benefit from taking on a more active role, engaging more with the team, and exploring opportunities to showcase her skills. She has a lot of potential, and with a little more involvement, she could make an even bigger impact. I have no doubt that with her confidence and abilities, she will excel in any task she takes on.

#### Hasan's reflections on Harvey
Working with Harvey on the SSH Cloud team for the Student Smart Homes project has been an enlightening experience. His strong leadership and organizational skills were pivotal in keeping our work structured and efficient. Harvey's ability to manage our GitHub repository, ensuring clear documentation and streamlined workflows, greatly benefited the entire group. Whenever there were questions or uncertainties, he was the go-to person, demonstrating both deep technical expertise and a willingness to assist others.

One of Harvey's standout strengths is his proactive approach. Not only did he take on a significant portion of the workload, but he also ensured that the team had the resources and guidance needed to progress effectively. For instance, his clear approach to organizing the database schema was crucial in ensuring seamless development. His attention to detail in code reviews and his ability to foresee potential issues saved the team considerable time and effort.

One area where Harvey could grow is in delegation. While his dedication to completing tasks is admirable, sharing responsibilities more evenly among team members could foster a more balanced workload and encourage others to contribute more actively. This would also help the team members develop skills in areas such as project organization and troubleshooting.

Looking ahead, I would suggest that Harvey continue leveraging his leadership skills while fostering a more collaborative environment by actively delegating tasks. This would not only enhance team productivity but also build a stronger, more cohesive team dynamic. Overall, Harvey's contributions were invaluable, and I look forward to collaborating with him on future projects.

---
---
