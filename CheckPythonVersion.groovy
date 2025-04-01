job('CheckPythonVersion') {
    description('This job checks Python version using two commands: py --version and py -0')

    triggers {
        cron('H/3 * * * *')  // Schedule the job to run every 3 minutes
    }

    steps {
        batchFile('py --version')  // Run the command to check Python version
        batchFile('py -0')         // Run the command to list all installed Python versions
    }

    // Add the job to a specific view after it's created
    configure { node ->
        def viewName = 'Versions of Programming Languages'
        def hudson = node / 'hudson'
        def views = hudson / 'views'
        def view = views.'hudson.model.ListView'.find { it.name.text() == viewName }
        if (view) {
            view / 'jobNames' << 'CheckPythonVersion'
        }
    }
}
